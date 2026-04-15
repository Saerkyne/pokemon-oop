# Code Review — `pokemonGame.core` Package

**Date:** 2026-04-09  
**Files reviewed:** `TypeChart.java`, `StatCalculator.java`, `Natures.java`, `EvManager.java`, `Stat.java`

---

## Summary

The core layer is 5 files, ~370 lines total. These are shared utilities used by almost every other package — `StatCalculator` and `Stat` are imported by 157 files each. The code is generally correct and well-organized. Most issues here are design-level improvements rather than bugs. The type chart is complete and accurate, natures are cleanly implemented, and stat formulas match the Gen III+ standard.

---

## Issues

### COR-1 · DESIGN · `TypeChart.TYPE_INDICES` HashMap duplicates `ordinal()`

**File:** `TypeChart.java`, lines 58–77

```java
private static final Map<Type, Integer> TYPE_INDICES = new HashMap<>();
static {
    TYPE_INDICES.put(Type.NORMAL, 0);
    TYPE_INDICES.put(Type.FIRE, 1);
    // ... through FAIRY = 17
}
```

The `Type` enum values are declared in the same order as the rows/columns of `TYPE_CHART`. This means `Type.NORMAL.ordinal() == 0`, `Type.FIRE.ordinal() == 1`, etc. — exactly the values in the HashMap. The entire HashMap (18 entries, autoboxing overhead, hash lookups) can be replaced with `ordinal()`.

**Why this matters (educational context):** Java enums have a built-in `ordinal()` method that returns their declaration order (0-based). When your data structure is already indexed to match that order, using `ordinal()` is both faster (array access vs. hash lookup) and less error-prone (impossible to map the wrong enum to the wrong index). The trade-off: if someone reorders the enum constants, the indices shift. A comment noting this dependency is sufficient protection.

**Fix:**
```java
public static float getEffectiveness(Type moveType, Type pokemonType) {
    if (pokemonType == Type.NONE || pokemonType == null) {
        return 1.0f;
    }
    // ordinal() matches TYPE_CHART row/column order — do not reorder Type enum
    return TYPE_CHART[moveType.ordinal()][pokemonType.ordinal()];
}
```

Then remove `TYPE_INDICES` entirely.

---

### COR-2 · DESIGN · `EvManager` mixes static and instance methods inconsistently

**File:** `EvManager.java`

| Method              | Static? | Needs instance state? |
|:--------------------|:-------:|:---------------------:|
| `getEv()`           | Yes     | No                    |
| `getTotalEv()`      | Yes     | No                    |
| `checkEvTotals()`   | Yes     | No                    |
| `setEv()`           | No      | No                    |
| `addEv()`           | No      | No                    |
| `evAddable()`       | No      | No                    |
| `evCapper()`        | No      | No                    |
| `recalcTotal()`     | No      | No                    |

None of these methods use instance state — all operate on the `Pokemon` parameter. Yet callers must instantiate `EvManager` to call `setEv()` or `addEv()`, while `getEv()` is callable statically. This forces code like `PokemonCRUD` to keep a `private static EvManager evManager = new EvManager()` field for no reason.

**Why this matters:** An inconsistent API surface confuses contributors. "Do I need `new EvManager()` or can I call `EvManager.getEv()` directly?" The answer depends on which method, and there's no obvious reason for the split.

**Fix:** Make all methods static and add `private EvManager() {}` to prevent instantiation:
```java
public final class EvManager {
    private EvManager() {}
    
    public static void setEv(Pokemon pokemon, Stat stat, int evValue) { ... }
    public static void addEv(Pokemon pokemon, Stat stat, int evToAdd) { ... }
    // etc.
}
```

---

### COR-3 · NIT · `StatCalculator` excessive `Math.floor()` calls

**File:** `StatCalculator.java`, lines 26–38

```java
int calcHP = (int) Math.floor(ev / 4.0);
calcHP = (int) Math.floor((2 * hpBase) + ivHp + calcHP);
calcHP = (int) Math.floor(calcHP * level);
calcHP = (int) Math.floor(calcHP / 100.0);
calcHP = (int) Math.floor(calcHP + level + 10);
```

For non-negative values (which all stat inputs are), `(int) Math.floor(x)` is equivalent to `(int) x` for doubles, and integer division already truncates for ints. The only meaningful floor is on the divisions (`ev / 4.0` and `calcHP / 100.0`). The other floor calls (`(2 * hpBase) + ivHp + calcHP` is a pure integer addition) are no-ops.

**Why this matters (educational context):** `Math.floor()` converts its argument to a `double` and returns a `double`. Wrapping integer additions in `Math.floor()` does nothing but obscure the code. Understanding which operations actually need rounding helps write clearer formulas.

**Fix — simplified version:**
```java
public static int calcMaxHP(int hpBase, int level, int ivHp, int ev) {
    int evContrib = ev / 4;
    int inner = (2 * hpBase) + ivHp + evContrib;
    return (inner * level) / 100 + level + 10;
}
```

---

### COR-4 · DESIGN · `StatusCondition.NONE` is unused with EnumSet storage

**File:** `TypeChart.java`, line 54

```java
public static enum StatusCondition {
    NONE, BURN, FREEZE, PARALYSIS, POISON, SLEEP
}
```

`Pokemon.java` uses `EnumSet<StatusCondition>` to track conditions. "No conditions" is represented by an empty set (`EnumSet.noneOf(StatusCondition.class)`). Having a `NONE` enum constant is a holdover from array-based or single-value storage. If someone adds `NONE` to the EnumSet, it would coexist with actual conditions (e.g., `{NONE, BURN}`), which is semantically meaningless.

**Fix:** Remove `NONE` from the enum. If existing code checks for `StatusCondition.NONE`, replace with `statusConditions.isEmpty()`.

---

### COR-5 · NIT · `TypeChart.getEffectiveness()` accepts `NONE` but the matrix doesn't include it

**File:** `TypeChart.java`, lines 80–82

```java
if (pokemonType == Type.NONE || pokemonType == null) {
    return 1.0f;
}
```

The guard handles `NONE` and `null` for the *defending* type, but there's no guard for the *attacking* type. If a move somehow has `Type.NONE`, `moveType.ordinal()` returns 18 (the 19th enum constant), causing an `ArrayIndexOutOfBoundsException` on the 18-row matrix.

**Fix:** Add the same guard for `moveType`:
```java
if (moveType == null || moveType == Type.NONE) {
    return 1.0f;
}
```

Or better: ensure `Type.NONE` can never appear as a move type (Move constructor validation).

---

## Stats

| Severity     | Count |
|:-------------|:-----:|
| DESIGN       |   3   |
| NIT          |   2   |
| **Total**    | **5** |
