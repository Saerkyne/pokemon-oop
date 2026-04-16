# Code Review — `pokemonGame.model` Package

**Date:** 2026-04-09  
**Files reviewed:** `Pokemon.java`, `Move.java`, `MoveSlot.java`, `LearnsetEntry.java`, `Team.java`, `Trainer.java`, `Battle.java`

---

## Summary

The model layer is the foundation of the project — 7 files, ~960 lines total. It holds pure domain data with no database or Discord dependencies (with one exception noted below). Overall structure is solid: constructors are `protected` to enforce species subclassing, records and unmodifiable lists are used in the right places, and the separation between base stats (immutable per species) and derived stats (recalculated) is clean. The issues below range from a crash-level bug to minor design concerns.

---

## Issues

### MDL-1 · BUG · `healToFull()` does not clear `isFainted`

**File:** `Pokemon.java`, lines 582–585

```java
public boolean healToFull() {
    this.currentHP = this.maxHP;
    return true;
}
```

When a Pokémon faints, `setCurrentHP()` sets `isFainted = true`. But `healToFull()` bypasses `setCurrentHP()` entirely — it directly writes `this.currentHP = this.maxHP` without resetting `isFainted`. After healing, `getIsFainted()` still returns `true` while `checkFainted()` (which checks `getCurrentHP() <= 0`) returns `false`. Any code path that checks `getIsFainted()` (e.g., `Battle.getAllRemainingPokemon()`) will treat the healed Pokémon as still fainted.

**Why this matters (educational context):** This is a classic "two sources of truth" problem. The faint state is derivable from HP (`currentHP <= 0`), but it's also stored redundantly in a boolean field. When one path updates HP without syncing the boolean, the two diverge. The fix is either (a) always go through `setCurrentHP()`, or (b) derive `isFainted` from HP instead of storing it.

**Fix:**

```java
public boolean healToFull() {
    this.currentHP = this.maxHP;
    this.isFainted = false;
    return true;
}
```

---

### MDL-2 · DESIGN · `checkFainted()` vs `getIsFainted()` inconsistency

**File:** `Pokemon.java`, lines 587–589

```java
public boolean checkFainted() {
    return this.getCurrentHP() <= 0;
}
```

`checkFainted()` derives faint status from HP. `getIsFainted()` reads the stored boolean. These can disagree (see MDL-1). Code throughout the project uses both — `TurnManager.resolveTurn()` uses `checkFainted()`, while `Battle.getAllRemainingPokemon()` uses `getIsFainted()`. If a Pokémon is healed, one says "fainted" and the other says "alive".

**Why this matters:** Having two methods that answer the same question differently is a maintenance trap. Future contributors won't know which to call.

**Fix (option A — derive from HP, remove the boolean):**

```java
public boolean isFainted() {
    return currentHP <= 0;
}
```

**Fix (option B — keep the boolean but always sync it):**
Ensure every path that changes HP (including `healToFull()`, `levelUp()`, any future heal/revive) updates `isFainted` consistently. Remove `checkFainted()` and use `getIsFainted()` everywhere.

---

### MDL-3 · ROBUSTNESS · EV setters bypass `EvManager` caps

**File:** `Pokemon.java`, lines 441–467

The direct setters (`setEvHp()`, `setEvAttack()`, etc.) accept any `int` without enforcing the per-stat cap of 252 or the total cap of 510. `EvManager.setEv()` and `addEv()` enforce these caps, but any code that calls the direct setters (e.g., `PokemonCRUD.mapResultSetToPokemon()` does go through `EvManager`, so it's safe today) can silently break the invariant.

**Why this matters:** If a future contributor uses `pokemon.setEvHp(999)` directly, no error is raised and the Pokémon ends up with illegal stats. The validation exists in `EvManager` but is easily bypassed.

**Fix:** Make the EV setters package-private or add validation:

```java
public void setEvHp(int evHp) {
    if (evHp < 0 || evHp > 252) {
        throw new IllegalArgumentException("EV must be 0-252, got: " + evHp);
    }
    this.evHp = evHp;
}
```

---

### MDL-4 · ROBUSTNESS · No validation on IV setters

**File:** `Pokemon.java`, lines 418–439

IVs must be 0–31, but the setters (`setIvHp()`, `setIvAttack()`, etc.) accept any `int`. `generateRandomIVs()` always produces valid values, but `PokemonCRUD.mapResultSetToPokemon()` calls these setters with database values — a corrupted DB row could silently create a Pokémon with IV 999.

**Fix:** Add range validation:

```java
public void setIvHp(int ivHp) {
    if (ivHp < 0 || ivHp > 31) {
        throw new IllegalArgumentException("IV must be 0-31, got: " + ivHp);
    }
    this.ivHp = ivHp;
}
```

---

### MDL-5 · DESIGN · `Move` fields should be `final`

**File:** `Move.java`, lines 19–24

```java
private String moveName;
private int movePower;
private Type moveType;
private Category moveCategory;
private int accuracy;
private int maxPp;
```

The Javadoc states "Move objects are immutable game-rule data", but the fields aren't `final`. There are no setters, so they're effectively immutable today, but `final` would make the compiler enforce the contract and prevent accidental mutation in subclasses.

**Why this matters (educational context):** The `final` keyword on fields serves as documentation and enforcement. Readers can see at a glance that the value never changes after construction. Without it, a reviewer must scan the entire class hierarchy to confirm immutability.

**Fix:** Add `final` to all six fields:

```java
private final String moveName;
private final int movePower;
// etc.
```

---

### MDL-6 · ROBUSTNESS · `Team.setTeamAsList()` stores direct reference

**File:** `Team.java`, line 69

```java
public void setTeamAsList(List<Pokemon> teamAsList) {
    this.teamAsList = teamAsList;
}
```

The getter returns `Collections.unmodifiableList(teamAsList)`, but the setter stores a direct reference to the caller's list. If the caller later mutates their list, the Team's internal state changes silently. This is called a "reference leak."

**Fix — defensive copy:**

```java
public void setTeamAsList(List<Pokemon> teamAsList) {
    this.teamAsList = new ArrayList<>(teamAsList);
}
```

---

### MDL-7 · DESIGN · Model layer imports service layer

**Files:** `Team.java` (line 7), `Trainer.java` (line 3), `Battle.java` (line 6)

`Team` imports `TeamService`, `Trainer` imports `TrainerService`, and `Battle` imports `BattleService`. These are used only in Javadoc `@see` annotations, but they create compile-time dependencies from model → service. The layered architecture says dependencies should flow inward: bot → service → model, never model → service.

**Why this matters:** If `TeamService` changes its package or class name, `Team.java` (a model class) would need to be recompiled. More importantly, it signals to readers that the model depends on the service layer, which isn't true at runtime.

**Fix:** Remove the imports and `@see` annotations referencing service classes. If cross-referencing is wanted, use plain text in the Javadoc:

```java
/** ... Coordinated by the TeamService class. */
```

---

### MDL-8 · BUG · `Battle.getAllRemainingPokemon()` NPE risk

**File:** `Battle.java`, lines 150–154

```java
public List<Pokemon> getAllRemainingPokemon(Trainer trainer, Team team) {
    return trainer.getTeam(team.getTeamName()).getTeamAsList().stream()
        .filter(p -> !p.getIsFainted())
        .toList();
}
```

`trainer.getTeam(teamName)` returns `null` if the trainer's in-memory team doesn't match the given name. The `.getTeamAsList()` call then throws `NullPointerException`. Since `Battle` is an ID-based container and `Trainer` only holds one team at a time, this mismatch is likely when the trainer's loaded team doesn't match the battle's team.

Also uses `getIsFainted()` which can be stale (see MDL-1).

**Fix:**

```java
public List<Pokemon> getAllRemainingPokemon(Trainer trainer, Team team) {
    Team loadedTeam = trainer.getTeam(team.getTeamName());
    if (loadedTeam == null) {
        LOGGER.warn("No team '{}' found on trainer '{}'", team.getTeamName(), trainer.getTrainerName());
        return List.of();
    }
    return loadedTeam.getTeamAsList().stream()
        .filter(p -> !p.checkFainted())
        .toList();
}
```

---

### MDL-9 · NIT · `Pokemon.random` is shared static `Random` — not thread-safe

**File:** `Pokemon.java`, line 151

```java
private static final Random random = new Random();
```

If multiple Discord users add Pokémon simultaneously (concurrent threads), the shared `Random` without synchronization can produce degraded randomness. Not a crash risk, but `ThreadLocalRandom.current()` is the idiomatic Java solution for concurrent random number generation.

**Fix:**

```java
// In generateRandomIVs():
this.ivHp = ThreadLocalRandom.current().nextInt(32);
```

---

### MDL-10 · NIT · `Trainer.getTeam()` only matches a single in-memory team

**File:** `Trainer.java`, lines 55–60

```java
public Team getTeam(String teamName) {
    if (team != null && team.getTeamName().equals(teamName)) {
        return team;
    }
    return null;
}
```

The schema supports multiple teams per trainer, but the domain model only holds one `Team` reference. If a trainer has teams "Alpha" and "Beta" but only "Alpha" is loaded in memory, `getTeam("Beta")` returns `null`. This is documented but creates a gap between the DB capabilities and the in-memory model.

**Not an immediate fix needed** — just something to be aware of as multi-team features develop.

---

## Stats

| Severity     | Count  |
|:-------------|:-----: |
| BUG          |   2    |
| DESIGN       |   3    |
| ROBUSTNESS   |   3    |
| NIT          |   2    |
| **Total**    | **10** |
