# Java 21 Code Review - Pokemon OOP

**Review Date:** 2026-04-20  
**Scope:** Updated live-code review of `battle`, `bot`, `core`, `db`, `model`, `moves`, `service`, and `species` packages  
**Java Version:** 21 (confirmed in [pom.xml](../pom.xml))  
**Validation:** Added inline `TODO(review 2026-04-20)` markers in source. Editor diagnostics were clean for touched files. Full Maven compile/test was not rerun in this pass.

---

## Summary

This codebase still has strong package boundaries and uses modern Java features well in several core places: battle actions are modeled with records, species learnsets are protected with unmodifiable views, and the Maven build is explicit about Java 21 and dependency versions. The largest remaining risk is not raw code quality; it is contract drift between layers. Battle persistence, battle action rehydration, and bot-driven battle flow are close enough to each other that small mismatches now create blocking bugs.

The highest priority work is concentrated in four places: battle effect correctness, battle persistence contract cleanup, bot orchestration cleanup, and domain modeling of nullable battle state. Every issue below has a matching inline source TODO tagged `TODO(review 2026-04-20)` where code should change.

---

## Battle Package

Package state is close to usable, but core battle correctness is still incomplete for non-damage actions and crit handling.

### 🔴 [Blocking] Status Moves Resolve as No-Ops

**File:** [Attack.java](../src/main/java/pokemonGame/battle/Attack.java#L101)

**What's wrong:**  
`Category.STATUS` moves return `0` damage and stop. There is no parallel path that applies sleep, paralysis, burn, stat changes, or other battle-state effects.

**Why this matters:**  
Many Gen 1 moves are status or control moves. Right now they appear to execute but do not change game state, which makes battle decisions misleading and prevents large parts of the move catalog from working.

**Recommended fix:**  
Minimal fix: route status moves through a dedicated resolver instead of returning immediately.

```java
if (move.getMoveCategory() == Category.STATUS) {
    return statusEffectResolver.resolve(attacker, defender, move);
}
```

More cohesive fix: introduce a move-effect pipeline so both damaging and non-damaging moves can attach secondary effects through the same turn-resolution path.

---

### 🔴 [Blocking] Critical Hit Math Does Not Match Stated 2x Intent

**File:** [Attack.java](../src/main/java/pokemonGame/battle/Attack.java#L152)

**What's wrong:**  
Critical hits currently double `level` before the damage formula instead of applying a direct critical-hit multiplier to the computed damage.

**Why this matters:**  
Doubling level changes a nonlinear formula, so the result drifts from a true $2\times$ critical hit. That creates a mismatch between code comments, battle expectations, and actual damage output.

**Recommended fix:**  
Compute base damage first, then apply a crit multiplier at one explicit step.

```java
int baseDamage = calculateBaseDamage(attacker, defender, move);
int critAdjustedDamage = crit ? baseDamage * 2 : baseDamage;
```

If exact placement relative to STAB, type, and random factor matters for your custom rules, document that order next to the multiplier.

---

### 🟡 [Important] SwitchAction Validates Slot Bounds, Not Battle Legality

**File:** [SwitchAction.java](../src/main/java/pokemonGame/battle/SwitchAction.java#L26)

**What's wrong:**  
The record constructor rejects out-of-range slots, but it still allows illegal battle actions like switching to the same Pokemon, switching to an empty slot, or switching to a fainted teammate.

**Why this matters:**  
Late validation pushes game-rule errors deeper into turn resolution, where they are harder to diagnose and may partially mutate battle state before failing.

**Recommended fix:**  
Add semantic validation in the record constructor or a factory method.

```java
Pokemon target = team.getTeamSlot(teamSlotIndex);
if (target == null || target.getIsFainted() || target == pokemon) {
    throw new IllegalArgumentException("Invalid switch target.");
}
```

---

## Bot Package

Bot layer works as a controller, but too much command orchestration is still concentrated in one listener and a few null-unsafe autocomplete chains.

### 🟡 [Important] SlashExample Still Owns Too Much Command Coordination

**File:** [SlashExample.java](../src/main/java/pokemonGame/bot/SlashExample.java#L49)

**What's wrong:**  
`SlashExample` still owns routing plus repeated trainer/team/Pokemon lookup and validation across handlers.

**Why this matters:**  
This makes every new command more expensive to add and harder to unit test. Shared validation logic also drifts over time because each handler can evolve slightly differently.

**Recommended fix:**  
Minimal fix: extract shared lookup helpers for trainer, team, and Pokemon retrieval.  
More cohesive fix: register one handler object per command and keep `SlashExample` as a router only.

```java
private final Map<String, Consumer<SlashCommandInteractionEvent>> handlers = Map.of(
    "startbattle", this::handleStartBattle,
    "addpokemon", this::handleAddPokemon
);
```

---

### 🟡 [Important] Destructive Database Clearing Bypasses Service Layer

**File:** [SlashExample.java](../src/main/java/pokemonGame/bot/SlashExample.java#L146)

**What's wrong:**  
The bot listener calls `DatabaseSetup.deleteAllData()` directly.

**Why this matters:**  
Authorization, audit rules, confirmation policy, and destructive-operation testing should live behind a service boundary. Direct DAO or setup calls from the bot layer make those safeguards harder to centralize.

**Recommended fix:**  
Move clear-database behavior into an admin-oriented service.

```java
adminService.clearDatabase(member);
event.reply("Database cleared successfully!").queue();
```

---

### 🟡 [Important] New Pokemon Are Still Forced to Level 50

**File:** [SlashExample.java](../src/main/java/pokemonGame/bot/SlashExample.java#L241)

**What's wrong:**  
`/addpokemon` still overrides the newly created Pokemon to level 50.

**Why this matters:**  
That creates production data that no longer matches species-constructor defaults and hides bugs that would surface at normal starting levels.

**Recommended fix:**  
Remove the override or guard it behind an explicit debug flag.

```java
if (debugConfig.forceLevel50Captures()) {
    newPokemon.setLevel(50);
    StatCalculator.calculateAllStats(newPokemon);
}
```

---

### 🟡 [Important] Autocomplete Still Has Null-Dereference Chains

**Files:** [AutoCompleteBot.java](../src/main/java/pokemonGame/bot/AutoCompleteBot.java#L250), [AutoCompleteBot.java](../src/main/java/pokemonGame/bot/AutoCompleteBot.java#L292)

**What's wrong:**  
Autocomplete builds chained calls like `getTeamFromName(...).getTeamDbId()` without checking whether the team lookup returned `null`.

**Why this matters:**  
These failures are masked by broad `catch (Exception)` blocks, so users get empty autocomplete choices instead of visible diagnostic behavior, and the real bug stays hidden.

**Recommended fix:**  
Split the chain into checked steps.

```java
Team selectedTeam = teamService.getTeamFromName(trainerDbId, teamName);
if (selectedTeam == null) {
    event.replyChoices(Collections.emptyList()).queue();
    return;
}
```

---

## Core Package

Core package is clean overall. Only one concrete correctness issue stood out in current live code.

### 🟢 [Suggestion] Recalculated Max HP Can Leave Impossible Current HP

**File:** [StatCalculator.java](../src/main/java/pokemonGame/core/StatCalculator.java#L72)

**What's wrong:**  
When recalculating stats after level, IV, or EV changes, `currentHP` is left untouched if `maxHP` already existed.

**Why this matters:**  
If max HP decreases, the Pokemon can end up with `currentHP > maxHP`, which breaks domain invariants and can confuse battle logic or UI formatting.

**Recommended fix:**  
Clamp current HP after recalculation.

```java
pokemon.setCurrentHP(Math.min(pokemon.getCurrentHP(), pokemon.getMaxHP()));
```

---

## DB Package

DB package has the most important architectural cleanup work left. Main issue is not SQL injection or resource leaks; it is persistence contract clarity.

### 🔴 [Blocking] Pending Switch Action Persists a Slot Index in a Column Named Like a Pokemon ID

**File:** [BattleTurnCRUD.java](../src/main/java/pokemonGame/db/BattleTurnCRUD.java#L42)

**What's wrong:**  
`switch_pokemon_id` is currently populated with `sa.teamSlotIndex()`.

**Why this matters:**  
The column name says "Pokemon ID" but the code stores a team slot number. That mismatch blocks reliable battle resume logic because hydration cannot know what the stored integer means.

**Recommended fix:**  
Minimal fix: rename the persisted concept in code and schema to `switch_team_slot_index`.  
Alternative fix: persist the actual Pokemon instance ID and let the service resolve the team slot later.

```java
pstmt.setInt(5, sa.teamSlotIndex()); // after schema rename to switch_team_slot_index
```

---

### 🔴 [Blocking] DAO Calls BattleService to Rehydrate Domain Actions

**File:** [BattleTurnCRUD.java](../src/main/java/pokemonGame/db/BattleTurnCRUD.java#L72)

**What's wrong:**  
`BattleTurnCRUD.getPendingActions()` reads rows and then calls `BattleService.createActionFromDbRow(...)`.

**Why this matters:**  
That reverses layer direction. DAO should return raw persistence data; service should decide how to hydrate domain objects.

**Recommended fix:**  
Return a small raw row type from the DAO and map it in the service layer.

```java
public record PendingActionRow(int trainerId, String actionType, Integer moveSlotIndex, Integer switchValue) {}
```

Then `BattleService` converts `PendingActionRow` into `MoveAction` or `SwitchAction`.

---

### 🟡 [Important] Battle Row Mapping Is Duplicated and Null-Unsafe

**File:** [BattleCRUD.java](../src/main/java/pokemonGame/db/BattleCRUD.java#L72)

**What's wrong:**  
`BattleCRUD` repeats the same manual mapping in multiple query methods and uses `ResultSet.getInt()` for fields that are nullable in SQL.

**Why this matters:**  
`getInt()` turns SQL `NULL` into `0`, which hides the difference between "not set yet" and an actual integer value. Repeated mapping also increases drift risk when `Battle` changes.

**Recommended fix:**  
Centralize mapping in one helper and use boxed reads for nullable columns.

```java
private Battle mapBattleRow(ResultSet rs) throws SQLException {
    Integer trainer1ActiveId = rs.getObject("trainer1_active_pokemon_id", Integer.class);
    // map once here
}
```

---

## Model Package

Model layer is still structurally strong, but two domain objects currently encode state too narrowly for the rest of the project.

### 🟡 [Important] Trainer Still Models Only One In-Memory Team

**File:** [Trainer.java](../src/main/java/pokemonGame/model/Trainer.java#L15)

**What's wrong:**  
`Trainer` still stores a single `Team team` field even though project rules allow multiple teams.

**Why this matters:**  
That mismatch leaks outward. A valid team can exist in the database while `trainer.getTeam(name)` still returns `null` because only one in-memory slot is available.

**Recommended fix:**  
Minimal fix: rename the field/API to `activeTeam` so the model is honest.  
More cohesive fix: model a collection of teams and expose active-team selection separately.

```java
private final Map<String, Team> teams = new HashMap<>();
```

---

### 🟡 [Important] Battle Uses Primitive Ints for SQL-Nullable State

**File:** [Battle.java](../src/main/java/pokemonGame/model/Battle.java#L40)

**What's wrong:**  
Fields like active Pokemon IDs and winner ID are modeled as primitive `int` even though their DB columns can be `NULL`.

**Why this matters:**  
Primitive fields force "unset" state to collapse to `0`, which makes battle lifecycle reasoning and persistence hydration less explicit.

**Recommended fix:**  
Use boxed types or a dedicated domain representation for unset state.

```java
private Integer trainer1ActivePokemonId;
private Integer trainer2ActivePokemonId;
private Integer winningTrainerId;
```

---

## Moves Package

Moves package is mostly consistent and low-risk. Main improvement is API discipline around immutable singleton move instances.

### 🟢 [Suggestion] Canonical Move Singletons Can Still Be Bypassed

**File:** [PokeMove.java](../src/main/java/pokemonGame/moves/PokeMove.java#L193)

**What's wrong:**  
The enum clearly intends to own canonical immutable move instances, but concrete move constructors remain public.

**Why this matters:**  
Any caller can still instantiate duplicate move objects directly, which weakens the singleton convention and creates unnecessary allocations.

**Recommended fix:**  
Make move constructors private or package-private where the enum singleton is the intended public access path.

```java
private Tackle() {
    super("Tackle", 40, Type.NORMAL, Category.PHYSICAL, 100, 35);
}
```

---

## Service Package

Service layer remains thin overall. Current blocking work is concentrated in battle rehydration and defensive validation around DB mapping.

### 🔴 [Blocking] Battle Action Rehydration Is Still a Null Stub

**File:** [BattleService.java](../src/main/java/pokemonGame/service/BattleService.java#L26)

**What's wrong:**  
`createActionFromDbRow(...)` still returns `null`.

**Why this matters:**  
Pending battle actions cannot be reliably resumed from persistence until this contract is real.

**Recommended fix:**  
First lock the raw DB contract. Then map it explicitly.

```java
return switch (actionType) {
    case "MOVE" -> new MoveAction(trainer, activePokemon, team, moveSlotIndex);
    case "SWITCH" -> new SwitchAction(trainer, activePokemon, team, switchSlotIndex);
    default -> throw new IllegalArgumentException("Unknown action type: " + actionType);
};
```

---

### 🟡 [Important] PokemonService Trusts Positional DAO Data Too Early

**File:** [PokemonService.java](../src/main/java/pokemonGame/service/PokemonService.java#L30)

**What's wrong:**  
`mapDbPokemonToObject(...)` reads positional indexes from `pokemonData` without validating size or null content first.

**Why this matters:**  
If DAO output changes or corrupted data slips through, failures happen deep inside rehydration as `IndexOutOfBoundsException` or `NullPointerException` rather than as clear mapper errors.

**Recommended fix:**  
Validate input shape up front.

```java
if (pokemonData == null || pokemonData.size() < 20) {
    throw new IllegalArgumentException("Incomplete Pokemon row data.");
}
```

Longer term, replace raw `List<Object>` with a typed row object or record.

---

## Species Package

Species package is in good shape overall. Learnset exposure is already disciplined. Only one factory-level suggestion stood out.

### 🟢 [Suggestion] PokemonFactory Swallows Construction Failures Too Broadly

**File:** [PokemonFactory.java](../src/main/java/pokemonGame/species/PokemonFactory.java#L38)

**What's wrong:**  
`createPokemonFromRegistry(...)` catches broad `Exception` and converts all failures into a `null` return.

**Why this matters:**  
That hides broken species wiring and makes debugging registry mistakes harder than necessary.

**Recommended fix:**  
Catch narrower exceptions or let unexpected construction errors fail loudly in tests/startup.

```java
catch (IllegalArgumentException | IllegalStateException e) {
    LOGGER.error("Failed to create Pokemon instance for species: {}", species.getDisplayName(), e);
    return null;
}
```

If species creation should never fail in normal runtime, prefer surfacing the exception instead of converting it to `null`.

---

## What's Done Well

1. Battle action modeling is strong. [MoveAction.java](../src/main/java/pokemonGame/battle/MoveAction.java) and [SwitchAction.java](../src/main/java/pokemonGame/battle/SwitchAction.java) use records and compact constructors in the right places.
2. Learnset encapsulation is consistent across species. [Abra.java](../src/main/java/pokemonGame/species/Abra.java) is representative: species expose `Collections.unmodifiableList(LEARNSET)` instead of leaking mutable lists.
3. Maven configuration is disciplined. [pom.xml](../pom.xml) pins Java 21, explicit dependency versions, and convergence enforcement.
4. Core stat and EV logic is still cleanly separated. [StatCalculator.java](../src/main/java/pokemonGame/core/StatCalculator.java) and [EvManager.java](../src/main/java/pokemonGame/core/EvManager.java) are focused utility classes instead of mixed I/O/business-logic code.
5. Database setup remains security-conscious. [DatabaseSetup.java](../src/main/java/pokemonGame/db/DatabaseSetup.java) uses environment/system-property config and a whitelist for dynamic table truncation.

---

## Recommended Next Steps

1. Fix battle persistence contract first. Start with [BattleTurnCRUD.java](../src/main/java/pokemonGame/db/BattleTurnCRUD.java#L42), [BattleTurnCRUD.java](../src/main/java/pokemonGame/db/BattleTurnCRUD.java#L72), [BattleService.java](../src/main/java/pokemonGame/service/BattleService.java#L26), and [BattleCRUD.java](../src/main/java/pokemonGame/db/BattleCRUD.java#L72).
2. Finish battle correctness second. Focus on [Attack.java](../src/main/java/pokemonGame/battle/Attack.java#L101), [Attack.java](../src/main/java/pokemonGame/battle/Attack.java#L152), and [SwitchAction.java](../src/main/java/pokemonGame/battle/SwitchAction.java#L26).
3. Refactor bot command flow third. Break shared validation and routing apart in [SlashExample.java](../src/main/java/pokemonGame/bot/SlashExample.java#L49) and fix autocomplete null handling in [AutoCompleteBot.java](../src/main/java/pokemonGame/bot/AutoCompleteBot.java#L250).
4. Clean up domain truthfulness after that. Revisit [Trainer.java](../src/main/java/pokemonGame/model/Trainer.java#L15) and [Battle.java](../src/main/java/pokemonGame/model/Battle.java#L40) so the model matches actual project rules and DB lifecycle.
