# Java 21 Code Review — Pokémon OOP

**Review Date:** 2026-04-08  
**Scope:** Full codebase review — model, battle, core, bot, persistence, service, and test layers  
**Java Version:** 21 (confirmed in `pom.xml` via `<maven.compiler.release>21</maven.compiler.release>`)  
**Build Status:** ✅ Compiles cleanly, 447 tests pass, 0 failures  

---

## Summary

This is a well-structured educational Java project that demonstrates solid OOP fundamentals: clean separation between model/battle/persistence/bot layers, proper use of sealed interfaces and records in the battle system, strong encapsulation in the `Pokemon` class, and comprehensive type chart testing. The codebase has evolved significantly — it now includes a functioning Discord bot, HikariCP connection pooling, a full service layer, and a battle turn system using Java 21 features (sealed interfaces, records, pattern matching).

The main areas needing attention are: (1) null-safety gaps throughout the bot layer and (2) 13 of 22 test files remain empty. The project's layered architecture is sound — most issues are localized and fixable without architectural changes.

---

## Issues

### ~~🔴 [Blocking] Division by Zero in Damage Calculation~~ — DISMISSED

**File:** `src/main/java/pokemonGame/battle/Attack.java` | **Line(s):** ~126

**Status:** ✅ Not a real issue. The Gen III+ stat formula (`StatCalculator.calcCurrentStat()`) guarantees a minimum stat value of **4** for any non-HP stat, even in the worst case (base 5, level 1, IV 0, EV 0, 0.9× nature): `floor((floor((2*5 + 0 + 0) * 1 / 100) + 5) * 0.9) = floor(4.5) = 4`. Division by zero is mathematically impossible given the stat calculation.

---

### ~~🔴 [Blocking] Critical Hit Formula Does Not Produce 2× Damage~~ — DISMISSED

**File:** `src/main/java/pokemonGame/battle/Attack.java` | **Line(s):** ~118–119

**Status:** ✅ Intentional design. This uses the **Gen 1 damage formula** approach where doubling the level produces a **level-scaling crit multiplier**, not a flat 2×. The ratio `(4L/5 + 2) / (2L/5 + 2)` yields:

- Level 5: `6/4` = **1.50×**
- Level 20: `18/10` = **1.80×**
- Level 95: `78/40` = **1.95×**

Crits grow stronger as Pokémon level up, approaching but never reaching 2×. This is the intended behavior.

---

### 🔴 [Blocking] `System.console()` I/O in Model Layer — PLANNED MIGRATION

**File:** `src/main/java/pokemonGame/model/MoveSlot.java` | **Line(s):** ~52–93

**Status:** 🚧 Known legacy code from before the Discord bot layer was added. Planned to be reworked so that move teaching is handled through Discord interactions (buttons/select menus) instead of CLI input. The method will be replaced by a Discord-component-based flow in the bot/service layer.

---

### ~~🔴 [Blocking] No Bounds Validation on MoveAction/SwitchAction Index Fields~~ — FIXED

**File:** `src/main/java/pokemonGame/battle/MoveAction.java` | **Line(s):** ~26  
**File:** `src/main/java/pokemonGame/battle/SwitchAction.java` | **Line(s):** ~14

**Status:** ✅ Fixed. Compact constructors now validate index bounds at record construction time.

---

### ~~🟡 [Important] SlashExample: 500+ Line God Method~~ — FIXED

**File:** `src/main/java/pokemonGame/bot/SlashExample.java`

**Status:** ✅ Fixed. Each command handler has been extracted into its own private method, with the switch statement delegating via single-line calls.

---

### 🟡 [Important] Null-Safety Gaps in Bot Layer

**File:** `src/main/java/pokemonGame/bot/SlashExample.java` | **Line(s):** scattered  
**File:** `src/main/java/pokemonGame/bot/AutoCompleteBot.java` | **Line(s):** scattered

**What's wrong:**  
Multiple `event.getOption("name")` calls are not null-checked before calling `.getAsString()` or `.getAsUser()`. JDA returns `null` when an optional parameter isn't provided. Examples:

- `event.getOption("opponent").getAsUser()` — NPE if "opponent" not provided
- `event.getOption("team").getAsString()` — NPE if "team" not provided
- `event.getOption("nickname")` — checked in some places but not others

**Why this matters:**  
NullPointerExceptions in event handlers crash the handler silently — the user sees Discord's "The application did not respond" error. JDA doesn't catch exceptions in listeners by default (they're logged but the interaction fails). Every `getOption()` call for optional parameters must be null-checked.

**Recommended fix:**  
Use a helper method or null-safe pattern:

```java
String teamName = Optional.ofNullable(event.getOption("team"))
    .map(OptionMapping::getAsString)
    .orElse(null);
```

---

### ~~🟡 [Important] Team Model: Redundant Dual Storage (List + 6 Named Slots)~~ — FIXED

**File:** `src/main/java/pokemonGame/model/Team.java`

**Status:** ✅ Fixed. Team now uses a single `List<Pokemon>` with index-based access, returning `Collections.unmodifiableList()` from the getter.

---

### ~~🟡 [Important] PokemonCRUD: Fragile Species Name ↔ Enum Conversion~~ — FIXED

**File:** `src/main/java/pokemonGame/db/PokemonCRUD.java`

**Status:** ✅ Fixed. DB now stores enum constant names (e.g., `PIKACHU`, `MRMIME`) for both species and natures. Reads use direct `valueOf()` with a try-catch safety net for corrupted data. The fragile regex conversion has been removed.

---

### 🟡 [Important] N+1 Query Pattern in TeamCRUD.getDBTeamForTrainer()

**File:** `src/main/java/pokemonGame/db/TeamCRUD.java` | **Line(s):** ~163–171

**What's wrong:**  
Inside the `while (rs.next())` loop, each iteration creates a new `TrainerCRUD` instance and calls `getTrainerByDbId()` — one extra DB query per Pokémon in the team. For a full 6-Pokémon team, this means 7 queries (1 for the team + 6 for the trainer lookup) instead of 2 (1 for team + 1 for trainer).

**Why this matters:**  
N+1 queries are the most common performance anti-pattern in ORM/DAO code. Each query involves network round-trip to the DB server, connection pool checkout, and result parsing. For a Discord bot that needs to respond within 3 seconds, unnecessary queries add latency.

**Recommended fix:**  
Load the trainer once before the loop:

```java
TrainerCRUD trainerCRUD = new TrainerCRUD();
Trainer trainer = trainerCRUD.getTrainerByDbId(trainerDbId);
while (rs.next()) {
    Pokemon pokemon = PokemonCRUD.mapResultSetToPokemon(rs, trainer);
    // ...
}
```

---

### 🟡 [Important] Static Random Not Thread-Safe

**File:** `src/main/java/pokemonGame/battle/Attack.java` | **Line(s):** ~rng field  
**File:** `src/main/java/pokemonGame/model/Pokemon.java` | **Line(s):** ~198

**What's wrong:**  
Both `Attack` and `Pokemon` use `private static Random rng = new Random()` as a shared static field. `Random` is thread-safe internally (its `nextInt()` uses CAS), but `Attack.setRng()` replaces the field reference without synchronization — if thread A writes a new `Random` while thread B reads from the old one, the behavior is undefined.

**Why this matters:**  
For a single-threaded bot, this doesn't currently cause problems. But if concurrent battles are ever supported (or tests run in parallel), corruption is possible. Using `ThreadLocalRandom` is the modern best practice for concurrent RNG.

**Recommended fix:**  
For tests that need deterministic RNG, make the field `volatile` and the setter `synchronized`, or inject the `Random` as a parameter instead of using a shared static.

---

### 🟡 [Important] TurnResult and DamageResult Accept Invalid States

**File:** `src/main/java/pokemonGame/battle/TurnResult.java`  
**File:** `src/main/java/pokemonGame/battle/DamageResult.java`

**What's wrong:**  
`TurnResult` allows `battleOver=true` with `winner=null` (battle ended but no winner). `DamageResult` allows `isHit=false` with `damage > 0` (missed but dealt damage) and `isCritical=true` (missed but was a crit). These are logically impossible states that records should prevent.

**Why this matters:**  
Records are value types — their whole purpose is to represent valid data. If invalid states are representable, every consumer must validate. Compact constructors are ideal for enforcing invariants at creation time.

**Recommended fix:**

```java
public record DamageResult(int damage, float effectiveness, boolean isCritical, boolean isHit, boolean defenderFainted) {
    public DamageResult {
        if (damage < 0) throw new IllegalArgumentException("damage must be >= 0");
        if (!isHit && damage > 0) throw new IllegalArgumentException("miss cannot deal damage");
        if (!isHit && isCritical) throw new IllegalArgumentException("miss cannot be critical");
    }
}
```

---

### 🟢 [Suggestion] Replace `java.sql.Timestamp` with `java.time.LocalDateTime`

**File:** `src/main/java/pokemonGame/model/Battle.java` | **Line(s):** ~32, 69

**What's wrong:**  
`Battle` uses `java.sql.Timestamp` for `startTime` and `updateTime`. `Timestamp` is mutable (extends `java.util.Date`), thread-unsafe, and part of the legacy JDBC API.

**Why this matters:**  
`java.time.LocalDateTime` (or `Instant` for UTC) is immutable, thread-safe, and the modern standard since Java 8. MariaDB JDBC driver supports `LocalDateTime` natively via `ResultSet.getObject(col, LocalDateTime.class)`.

---

### 🟢 [Suggestion] Use Enhanced Switch in Bot Layer

**File:** `src/main/java/pokemonGame/bot/SlashExample.java`  
**File:** `src/main/java/pokemonGame/bot/AutoCompleteBot.java`

**What's wrong:**  
Both files use traditional `if/else if` chains or `switch` with `case/break` syntax for routing commands. Java 21's arrow-syntax switch is cleaner and eliminates fall-through bugs.

**Recommended fix:**

```java
switch (event.getName()) {
    case "say"           -> handleSay(event);
    case "ping"          -> event.reply("Pong!").queue();
    case "createtrainer"  -> handleCreateTrainer(event);
    default              -> event.reply("Unknown command").setEphemeral(true).queue();
};
```

---

### 🟢 [Suggestion] Remove Redundant `getActionType()` from Sealed Interface

**File:** `src/main/java/pokemonGame/battle/BattleAction.java`

**What's wrong:**  
`BattleAction` defines `getActionType()` returning `"MOVE"` or `"SWITCH"` as strings. Since `BattleAction` is a sealed interface with only `MoveAction` and `SwitchAction` as permits, pattern matching makes this method unnecessary. The string comparison is a type-safety regression.

**Recommended fix:**  
Remove `getActionType()` and use pattern matching everywhere:

```java
switch (action) {
    case MoveAction ma   -> resolveMove(ma, defender);
    case SwitchAction sa -> resolveSwitch(sa);
}
```

---

### 🟢 [Suggestion] EvManager: Extract Magic Numbers to Constants

**File:** `src/main/java/pokemonGame/core/EvManager.java`

**What's wrong:**  
The per-stat cap (252) and total cap (510) are hardcoded throughout the class. If these values ever change (unlikely, but possible for custom rulesets), every occurrence must be found and updated.

**Recommended fix:**

```java
private static final int MAX_EV_PER_STAT = 252;
private static final int MAX_EV_TOTAL = 510;
```

---

### 📚 [Learning] Sealed Interfaces + Records — Excellent Usage in Battle System

**File:** `src/main/java/pokemonGame/battle/BattleAction.java`  
**File:** `src/main/java/pokemonGame/battle/MoveAction.java`  
**File:** `src/main/java/pokemonGame/battle/SwitchAction.java`

**What's done well:**  
The battle action hierarchy is a textbook example of Java 17+ sealed interfaces with record implementations:

```java
sealed interface BattleAction permits MoveAction, SwitchAction { ... }
record MoveAction(...) implements BattleAction { ... }
record SwitchAction(...) implements BattleAction { ... }
```

This gives you:

- **Compile-time exhaustiveness** — a `switch` on `BattleAction` that misses a case won't compile.
- **Immutability** — records are final, fields are final, no mutation.
- **Reduced boilerplate** — constructor, getters, `equals()`, `hashCode()`, `toString()` all auto-generated.

This is exactly the pattern recommended for command/action types in game engines. It replaces the older Visitor pattern with something far simpler.

---

### 📚 [Learning] HikariCP Connection Pooling — Properly Implemented

**File:** `src/main/java/pokemonGame/db/DatabaseSetup.java`

**What's done well:**  

- Uses HikariCP instead of opening raw connections per query (a common mistake in learning projects)
- Credentials loaded from environment variables, not hardcoded
- Shutdown hook registered to close the pool on JVM exit
- `resolveConfig()` checks both system properties and environment variables (supports both `-D` flags and env vars)
- `ALLOWED_TABLES` whitelist prevents SQL injection in `deleteAllData()` even though table names can't use parameter placeholders

---

### 📚 [Learning] Pattern Matching in BattleTurnCRUD — Good Use of Java 21

**File:** `src/main/java/pokemonGame/db/BattleTurnCRUD.java` | **Line(s):** ~71

**What's done well:**  
`submitPendingAction()` uses sealed-type pattern matching to extract fields from `BattleAction`:

```java
switch (action) {
    case MoveAction ma -> { /* extract moveSlotIndex */ }
    case SwitchAction sa -> { /* extract teamSlotIndex */ }
}
```

This is the correct modern approach — no `instanceof` chains, no casting, no `getActionType()` string checks.

---

## What's Done Well

1. **Layered architecture** — Model, battle, core, service, persistence, and bot layers are clearly separated. Model classes don't import JDA or `java.sql`. Service layer coordinates between DAOs and domain. Bot layer is thin (delegates to services). This is textbook enterprise architecture applied to a learning project.

2. **Prepared statements everywhere** — Every SQL query across all CRUD classes uses `?` parameter placeholders. No string concatenation for SQL. This eliminates the #1 web vulnerability (SQL injection) entirely.

3. **Try-with-resources for all DB resources** — Every `Connection`, `PreparedStatement`, and `ResultSet` is wrapped in try-with-resources. No resource leaks. This is the correct pattern and many professional codebases get it wrong.

4. **Comprehensive type chart testing** — `TypeChartTest.java` has 500+ lines of parameterized tests covering all 18 types across super-effective, not-very-effective, immune, and neutral matchups. This is one of the most thorough test suites in the project.

5. **Good use of modern Java features** — Sealed interfaces, records, pattern matching in switch, enhanced switch syntax, `EnumSet` for status conditions, `Collections.unmodifiableList()` for defensive copies. The project doesn't just use Java 21 — it uses it idiomatically.

6. **Clean Pokémon/Move class hierarchy** — `Pokemon` as a base class with protected constructors, species as subclasses, moves as abstract with concrete implementations. The `PokeSpecies` enum factory pattern is well-designed with alias support.

7. **Thorough EV management** — `EvManager` correctly enforces both per-stat (252) and total (510) caps, with separate setter and adder behaviors. Both are extensively tested.

8. **Service layer exists and is used** — `TrainerService`, `TeamService`, and `BattleService` coordinate between DAOs and handle multi-step operations. The bot layer calls services, not DAOs directly (with minor exceptions in `AutoCompleteBot`).

---

## Test Coverage Analysis

**Current state:** 447 tests pass, 0 failures. 9 of 22 test files are implemented.

| Layer | Implemented Tests | Empty Test Files | Coverage |
| ------- | ------------------ | ----------------- | ---------- |
| Model | PokemonTest, LearnsetEntryTest | MoveSlotTest, TeamTest | 37.5% |
| Core | TypeChartTest, NaturesTest, EvSetterTest, EvAdderTest | — | 100% |
| Battle | AttackTest, TurnManagerTest | — | 75% |
| Database | TrainerCRUDTest | PokemonCRUDTest, TeamCRUDTest, BattleTurnCRUDTest | 25% |
| Service | — | TrainerServiceTest, TeamServiceTest, BattleServiceTest | 0% |
| Bot | — | BotRunnerTest, SlashExampleTest | 0% |
| Species | — | PokeSpeciesTest, PokeFactoryTest | 0% |

### Tests to Add (Priority Order)

1. **MoveSlotTest** — PP tracking (`use()`, `restore()`, `getCurrentPP()`, zero-PP behavior). Core to battle mechanics.
2. **TeamTest** — Slot management, `add()`/`remove()`, `isFull()`, slot retrieval, reordering. Underlies all team operations.
3. **AttackTest enhancements** — Type effectiveness in damage formula (Fire vs Grass = 2×), STAB multiplier, crit damage verification, dual-type effectiveness.
4. **PokemonCRUDTest** — Pokémon persistence round-trip (stats, IVs, EVs, nickname, species, movesets). Critical for data integrity.
5. **TeamCRUDTest** — Add/remove Pokémon from DB team, slot reordering, full-team rejection.
6. **PokeSpeciesTest / PokeFactoryTest** — All 151 species constructible, base stats correct, factory name resolution (including aliases).
7. **Service layer tests** — TrainerService duplicate rejection, TeamService add/release workflows, BattleService challenge creation.
8. **StatCalculator edge cases** — Level 1, level 100, 0 base stat, max IV + max EV, nature modifiers at boundaries.

---

## Recommended Next Steps

### Immediate (Fix Before Next Feature Work)

1. **Add compact constructors to `TurnResult`, `DamageResult`** — Validate invariants at creation.

### Short-Term (Next 2–3 Sessions)

1. **Decompose `SlashExample.onSlashCommandInteraction()`** — Extract per-command handler methods.
2. **Add null-checks on all `event.getOption()` calls** in `SlashExample` and `AutoCompleteBot`.
3. **Implement `MoveSlotTest` and `TeamTest`** — Fill the two most critical model test gaps.
4. **Fix Team model** — Remove the six named slot fields; use `List<Pokemon>` only.
5. **Fix TeamCRUD N+1 query** — Pre-load trainer before the loop.

### Medium-Term (Ongoing)

1. **Implement remaining DB tests** (PokemonCRUD, TeamCRUD) to protect data integrity.
2. **Replace `java.sql.Timestamp` with `java.time.LocalDateTime`** in `Battle`.
3. **Move `BattleService.checkFainted()` and `LearnsetEntry.getEligibleMoves()` to service layer** to clean up model responsibilities.
4. **Implement status effects** (burn, paralysis, poison, sleep, freeze) — the `StatusCondition` enum and `EnumSet` are already in place.
5. **Consider `Optional<T>` returns** instead of null from service/DAO methods to make null-handling explicit.
