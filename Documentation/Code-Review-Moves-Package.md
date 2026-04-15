# Code Review — `pokemonGame.moves` Package

**Date:** 2026-04-09  
**Files reviewed:** `PokeMove.java`, `Tackle.java`, `Thunderbolt.java` (as representative pattern for all 165 move subclasses)

---

## Summary

The moves package is 166 files: the `PokeMove` enum (165 entries) and 165 move subclasses. Each subclass extends `Move`, declares a `public static final INSTANCE` singleton, and calls `super()` with the move's fixed stats (name, power, type, category, accuracy, PP). The pattern is clean and consistent. Special effects are documented in comments but not yet implemented. Issues are minor — mostly about enforcing the immutability contract that the code already intends.

---

## Issues

### MOV-1 · DESIGN · `Move` fields should be `final` to match immutability intent

**File:** `Move.java`, lines 19–24 (cross-reference: also raised as MDL-5 in Model review)

```java
private String moveName;
private int movePower;
private Type moveType;
private Category moveCategory;
private int accuracy;
private int maxPp;
```

Move subclasses are used as singletons (`public static final Tackle INSTANCE = new Tackle()`). If a singleton's fields are accidentally mutated, every Pokémon using that move is affected. Making fields `final` prevents this at the language level.

**Fix:** Add `final` to all six fields in `Move.java`.

---

### MOV-2 · DESIGN · `PokeMove.createMove()` name is misleading — returns shared singleton

**File:** `PokeMove.java`, line 211

```java
public Move createMove() {
    return move;
}
```

The method name "create" implies a new instance is constructed. In reality, it returns the same `INSTANCE` singleton every time (e.g., `Tackle.INSTANCE`). This is correct behavior (moves are immutable game data), but the naming could mislead a contributor into thinking each call produces a fresh `Move`.

**Fix:** Rename to `getMove()` or `getMoveInstance()`:
```java
public Move getMove() {
    return move;
}
```

---

### MOV-3 · DESIGN · `PokeMove.fromString()` uses O(n) linear scan

**File:** `PokeMove.java`, lines 217–223

```java
public static PokeMove fromString(String moveName) {
    for (PokeMove move : PokeMove.values()) {
        if (move.displayName.equalsIgnoreCase(moveName)) {
            return move;
        }
    }
    throw new IllegalArgumentException("No move found with name: " + moveName);
}
```

This iterates all 165 moves on every call. `fromString()` is called by:
- `PokemonCRUD.mapResultSetToPokemon()` — for every move of every Pokémon loaded from the DB
- `MoveSlotService.getMoveByName()` — from bot layer commands

A pre-built `Map<String, PokeMove>` would make this O(1):

```java
private static final Map<String, PokeMove> BY_NAME = new HashMap<>();
static {
    for (PokeMove pm : values()) {
        BY_NAME.put(pm.displayName.toLowerCase(), pm);
    }
}

public static PokeMove fromString(String moveName) {
    PokeMove found = BY_NAME.get(moveName.toLowerCase());
    if (found == null) {
        throw new IllegalArgumentException("No move found with name: " + moveName);
    }
    return found;
}
```

---

### MOV-4 · NIT · Status move effects documented but not implemented

**Files:** `Thunderbolt.java`, and many other move subclasses

```java
// == Special Effect (Not Yet Implemented) ==
// 10% chance to paralyze the target.
```

This is documented as work-in-progress and tracked in the project's known issues. Included here for completeness — the comments are well-written and correctly describe the intended effects. When implemented, these effects should be applied in `TurnManager.resolveMove()` after damage is dealt.

---

### MOV-5 · NIT · Move subclasses are boilerplate-heavy for data-only classes

Each of the 165 move files follows the exact same pattern:
```java
public class Tackle extends Move {
    public static final Tackle INSTANCE = new Tackle();
    public Tackle() {
        super("Tackle", 40, Type.NORMAL, Category.PHYSICAL, 100, 35);
    }
}
```

This is 165 files with 8–12 lines each, totaling ~1,500 lines for what is essentially tabular data. An alternative would be defining moves in the `PokeMove` enum directly (it already stores the `Move` instance) and having the enum create anonymous `Move` subclasses. However, the current approach has merit:
- Each file is trivially simple and self-contained
- Special effects (when implemented) can be overridden per subclass
- The `INSTANCE` pattern is standard Java singleton

**Not a fix needed** — just a design observation. The current pattern will scale fine for Gen 1's 165 moves.

---

## Stats

| Severity     | Count |
|:-------------|:-----:|
| DESIGN       |   3   |
| NIT          |   2   |
| **Total**    | **5** |
