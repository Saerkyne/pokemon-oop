# Code Review — `pokemonGame.species` Package

**Date:** 2026-04-09  
**Files reviewed:** `PokeSpecies.java`, `PokemonFactory.java`, `Abra.java` (as representative pattern for all 151 species subclasses)

---

## Summary

The species package is 153 files: the `PokeSpecies` enum (151 entries), `PokemonFactory`, and 151 species subclasses. The subclass pattern is consistent — each species extends `Pokemon`, declares a static `LEARNSET`, calls `super()` with base stats, and initializes IVs/stats in the constructor. `PokeSpecies` cleanly maps display names + aliases to constructor references. The main concerns are a mutable shared learnset that can be corrupted, and redundant lookup mechanisms between `PokeSpecies` and `PokemonFactory`.

---

## Issues

### SPC-1 · BUG · Species `LEARNSET` is mutable — shared game data can be corrupted

**File:** `Abra.java` (and all 151 species), line 15 + `getLearnset()` override

```java
private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
static {
    LEARNSET.add(new LearnsetEntry(Teleport.INSTANCE, Source.LEVEL, 1));
    // ... more entries
}

@Override
public List<LearnsetEntry> getLearnset() {
    return LEARNSET;
}
```

The `final` on `LEARNSET` prevents reassignment, but `ArrayList` is mutable. `getLearnset()` returns the raw list **without** an unmodifiable wrapper. Any caller can do `abra.getLearnset().add(...)` or `.clear()` and corrupt the shared learnset for **all** Abra instances. The base class `Pokemon.getLearnset()` returns `Collections.unmodifiableList(learnset)`, but species overrides bypass this protection.

**Why this matters (educational context):** This is the "mutable shared state" problem. `static final` means the reference can't change, but the **contents** of the collection can. It's like a `final` pointer to a whiteboard — you can't change which whiteboard it points to, but anyone can erase and rewrite what's on it.

**Fix (in each species class):**

```java
@Override
public List<LearnsetEntry> getLearnset() {
    return Collections.unmodifiableList(LEARNSET);
}
```

Or wrap once during static init:

```java
private static final List<LearnsetEntry> LEARNSET;
static {
    List<LearnsetEntry> list = new ArrayList<>();
    list.add(new LearnsetEntry(Teleport.INSTANCE, Source.LEVEL, 1));
    // ...
    LEARNSET = Collections.unmodifiableList(list);
}
```

---

### SPC-2 · DESIGN · `PokemonFactory` registry is redundant with `PokeSpecies`

**File:** `PokemonFactory.java`

`PokeSpecies` already holds constructor references and provides `getSpeciesByString()` for name-based lookup. `PokemonFactory` builds a **separate** `HashMap<String, Function<String, Pokemon>>` from the same `PokeSpecies` data. Two parallel lookup mechanisms exist:

1. `PokeSpecies.getSpeciesByString("Abra")` → returns `PokeSpecies.ABRA` → `.createPokemon(name)`
2. `PokemonFactory.createPokemonFromRegistry(PokeSpecies.ABRA, name)` → looks up in HashMap → calls constructor

If a species is added to the enum but `PokemonFactory`'s HashMap doesn't pick it up (e.g., due to a constructor exception during static init), the two paths give different results.

**Why this matters:** Maintaining two registries for the same data violates DRY (Don't Repeat Yourself). Bugs can arise when one is updated and the other isn't.

**Fix — option A (consolidate into `PokeSpecies`):**

```java
// In PokeSpecies:
public static Pokemon create(String speciesInput, String nickname) {
    PokeSpecies species = getSpeciesByString(speciesInput);
    return species != null ? species.createPokemon(nickname) : null;
}
```

Then remove `PokemonFactory` or make it delegate to `PokeSpecies`.

**Fix — option B (make `PokemonFactory` the single entry point):**
Remove `PokeSpecies.getSpeciesByString()` and route everything through `PokemonFactory`.

---

### SPC-3 · DESIGN · `PokeSpecies.getSpeciesByString()` uses O(n) linear scan

**File:** `PokeSpecies.java`, lines 203–226

```java
public static PokeSpecies getSpeciesByString(String input) {
    String norm = input.trim().toLowerCase();
    for (PokeSpecies species : PokeSpecies.values()) {
        if (species.getDisplayName().toLowerCase().equals(norm)) { return species; }
        for (String alias : species.getAliases()) {
            if (alias.toLowerCase().equals(norm)) { return species; }
        }
    }
    return null;
}
```

This iterates all 151 species and their aliases on every call. `PokemonFactory` already has an O(1) HashMap for the same purpose. If `getSpeciesByString()` is used in autocomplete handlers (called on every keystroke), this linear scan runs 151+ comparisons per keystroke.

**Fix:** Build a static HashMap once at class init:

```java
private static final Map<String, PokeSpecies> LOOKUP = new HashMap<>();
static {
    for (PokeSpecies s : values()) {
        LOOKUP.put(s.getDisplayName().toLowerCase(), s);
        for (String alias : s.getAliases()) {
            LOOKUP.put(alias.toLowerCase(), s);
        }
    }
}

public static PokeSpecies getSpeciesByString(String input) {
    return input == null ? null : LOOKUP.get(input.trim().toLowerCase());
}
```

This also ties into SPC-2 — if one lookup path is optimized, it should be the canonical one.

---

### SPC-4 · NIT · `PokemonFactory` logs at INFO during creation — verbose in production

**File:** `PokemonFactory.java`, lines 42 and 54

```java
LOGGER.info("Created alias: {} for species: {}", alias, species.getDisplayName());
// ...
LOGGER.info("Created species: {} with class: {}", species.getDisplayName(), species.getClassName());
```

These log messages fire on every `createPokemonFromRegistry()` call (every `/addpokemon` command). In production with many players, this generates a lot of noise. The static block registration logs are fine (they run once at startup), but the per-creation logs should be `DEBUG`.

---

### SPC-5 · NIT · Redundant `displayName` and `className` fields in `PokeSpecies`

**File:** `PokeSpecies.java`

For 147 of 151 species, `displayName` and `className` are identical (e.g., `"Abra"` and `"Abra"`). Only Farfetch'd, Mr. Mime, NidoranF, and NidoranM differ. The `className` field exists to provide a Java-safe identifier, but it's only used in a log message in `PokemonFactory`. The constructor reference (`Abra::new`) already handles creation — `className` isn't needed for instantiation.

**Low priority** — the duplication is minimal and doesn't cause bugs.

---

## Stats

| Severity     | Count |
|:-------------|:-----:|
| BUG          |   1   |
| DESIGN       |   2   |
| NIT          |   2   |
| **Total**    | **5** |
