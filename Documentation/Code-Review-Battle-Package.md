# Code Review — `pokemonGame.battle` Package

**Date:** 2026-04-09  
**Files reviewed:** `Attack.java`, `TurnManager.java`, `BattleAction.java`, `MoveAction.java`, `SwitchAction.java`, `DamageResult.java`, `TurnResult.java`

---

## Summary

The battle layer is 7 files, ~640 lines total. The architecture is well-structured: a sealed interface with record implementations for actions, stateless damage calculation in `Attack`, and turn resolution in `TurnManager`. The educational comments in `BattleAction.java` are excellent. However, there are several bugs in `TurnManager.resolveMove()` where game state is modified before validation, and a critical faint-check logic error in `resolveTurn()`.

---

## Issues

### BTL-1 · BUG · Faint-check loop in `resolveTurn()` incorrectly ends the battle

**File:** `TurnManager.java`, lines 82–97

```java
for (Pokemon p : defendingTeam.getTeamAsList()) {
    if (!p.checkFainted()) {
        LOGGER.info("...");
        break;
    }
    battleOver = true;
    winner = (firstAction == trainer1Action) ? firstAction.trainer() : secondAction.trainer();
    LOGGER.info("...");
}
```

This loop is supposed to check whether the defending team has any non-fainted Pokémon left. The intent is "if all are fainted, battle is over." But the logic is inverted: it sets `battleOver = true` for **every fainted Pokémon** encountered before a non-fainted one.

**Example:** Team = [Fainted, Alive, Alive].

- Iteration 1: `p[0]` is fainted → skip `break` → set `battleOver = true`
- Iteration 2: `p[1]` is alive → `break`
- Result: `battleOver = true` even though 2 Pokémon are alive.

The battle ends incorrectly whenever the defending team's first Pokémon has fainted, regardless of how many others survive.

**Fix:**

```java
boolean allFainted = defendingTeam.getTeamAsList().stream()
    .allMatch(Pokemon::checkFainted);
if (allFainted) {
    battleOver = true;
    Trainer attackingTrainer = (firstAction == trainer1Action)
        ? firstAction.trainer() : secondAction.trainer();
    winner = attackingTrainer;
}
```

---

### BTL-2 · BUG · `resolveMove()` deals damage before checking accuracy

**File:** `TurnManager.java`, lines 150–165

```java
int damageDealt = dealDamage(action.activePokemon(), defender, action.getMove());
// ...
boolean isHit = Attack.checkAccuracy(action.activePokemon(), defender, action.getMove());
```

`dealDamage()` subtracts HP from the defender unconditionally. Then `Attack.checkAccuracy()` is called **after** to populate the `DamageResult`. If accuracy says "miss", the `DamageResult` would report `isHit=false` — but the damage was already applied. The defender lost HP from a move that supposedly missed.

**Why this matters:** The `DamageResult` record validates that `!isHit && damage != 0` is illegal (throws `IllegalArgumentException`). So if the move "misses" but dealt damage, constructing the `DamageResult` would crash. In practice, `damageDealt > 0` is checked first, so the crash path is: move deals damage → accuracy check says miss → `new DamageResult(damageDealt, ..., false, ...)` → exception.

**Fix:** Check accuracy **before** dealing damage:

```java
boolean isHit = Attack.checkAccuracy(action.activePokemon(), defender, action.getMove());
if (!isHit) {
    return new DamageResult(0, 1.0f, false, false, false);
}
int damageDealt = dealDamage(action.activePokemon(), defender, action.getMove());
```

---

### BTL-3 · BUG · Double crit roll — `DamageResult.isCritical` doesn't match actual damage

**File:** `TurnManager.java`, lines 160–163

```java
int damageDealt = dealDamage(action.activePokemon(), defender, action.getMove());
// ...
boolean isCritical = Attack.calculateCriticalHit(action.activePokemon(), defender);
```

`dealDamage()` calls `Attack.calculateDamage()`, which internally rolls `calculateCriticalHit()` (roll #1) and uses it to double the level factor. Then `resolveMove()` calls `calculateCriticalHit()` **again** (roll #2) to populate `DamageResult.isCritical`. These are independent random rolls — the `DamageResult` may say "critical hit" when the damage didn't include the crit multiplier, or vice versa.

**Why this matters:** The bot layer will display "Critical hit!" to the player based on `DamageResult.isCritical`, but the actual damage number could be different.

**Fix:** `Attack.calculateDamage()` should return a result that includes whether a crit occurred, rather than having the caller roll separately. For example, return a record:

```java
public record DamageCalcResult(int damage, boolean wasCritical) {}
```

Or pass the crit flag in from outside so the same roll is used everywhere.

---

### BTL-4 · BUG · `DamageResult.effectiveness` only captures primary type

**File:** `TurnManager.java`, line 161

```java
float effectiveness = Attack.calculateEffectiveness(defender.getTypePrimary(), action.getMove());
```

For dual-type Pokémon, the actual effectiveness used in damage calculation is `primary * secondary` (computed inside `Attack.calculateDamage()`). But only the primary effectiveness is stored in `DamageResult`. A Water move vs. Ground/Rock Pokémon deals 4× damage, but `DamageResult.effectiveness` would say `2.0f`.

**Fix:** Either pass combined effectiveness from the damage calculator, or compute it here:

```java
float effectivenessPrimary = Attack.calculateEffectiveness(defender.getTypePrimary(), action.getMove());
float effectivenessSecondary = Attack.calculateEffectiveness(defender.getTypeSecondary(), action.getMove());
float combinedEffectiveness = effectivenessPrimary * effectivenessSecondary;
```

Note: the `DamageResult` validation allows 0, 0.25, 0.5, 1, 2, and 4 — these are exactly the possible combined values, so validation wouldn't need to change.

---

### BTL-5 · BUG · Division by zero in `Attack.calculateDamage()`

**File:** `Attack.java`, line 148

```java
int baseDamage = (levelCalc * power * attackStat) / defenseStat;
```

`defenseStat` comes from `defender.getDefenseStatForMove(move)`. At level 5 with 0 IVs, 0 EVs, and a lowering nature, the formula `((2*base + 0 + 0) * 5 / 100 + 5) * 0.9` could produce a value that floors to 0 for Pokémon with very low base defense. If `defenseStat` is 0, this line throws `ArithmeticException`.

This is a known issue from the previous code review (item #1 in Code-Review-2026-04-08.md) but is included here for completeness.

**Fix:**

```java
int defenseStat = Math.max(1, defender.getDefenseStatForMove(move));
```

---

### BTL-6 · DESIGN · `Math.random()` in `getFirstAction()` breaks test determinism

**File:** `TurnManager.java`, lines 145–147

```java
if (Math.random() < 0.5) {
    return trainer1Action;
}
```

`Attack.java` uses a seedable `Random` via `setRng()` for test reproducibility. But the speed-tie coin flip in `TurnManager` uses `Math.random()`, which cannot be seeded. Tests involving speed ties will be non-deterministic.

**Fix:** Use the same `rng` from `Attack`, or accept a `Random` parameter:

```java
if (Attack.randomInt(0, 1) == 0) {
    return trainer1Action;
}
```

---

### BTL-7 · DESIGN · `resolveMove()` fallback `DamageResult` has misleading values

**File:** `TurnManager.java`, line 175

```java
return new DamageResult(0, 0, false, true, false);
```

When `damageDealt == 0` (e.g., type immunity or status move), this returns `effectiveness=0` and `isHit=true`. But the damage might be 0 because the defender was already at 0 HP, not because of type immunity. The effectiveness should match the actual type matchup.

---

### BTL-8 · NIT · `TurnManager` should have a private constructor

**File:** `TurnManager.java`, lines 61–63

```java
public TurnManager() {
}
```

All methods in `TurnManager` are static. The empty public constructor allows instantiation (`new TurnManager()`) which serves no purpose. `Attack.java` correctly has `private Attack() {}` — `TurnManager` should follow the same pattern.

**Fix:**

```java
private TurnManager() {}
```

---

## Stats

| Severity     | Count |
|:-------------|:-----:|
| BUG          |   5   |
| DESIGN       |   2   |
| NIT          |   1   |
| **Total**    | **8** |
