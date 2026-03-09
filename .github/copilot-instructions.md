# Copilot Instructions — Pokemon OOP

## Project Purpose

This is a **learning project** for Java and Object-Oriented Programming concepts. It is a modified, text-based re-implementation of Pokémon Red. Only **Gen 1 Pokémon and moves** (original 151 + their moves) are in scope, but several mechanics are modernized: the Special stat is split into Sp.Atk/Sp.Def, IVs and EVs use the modern 0-31 / 0-252 system, natures exist, and the critical-hit formula is custom (speed-differential-based, see `Attack.calculateCriticalHit`). When suggesting changes, **explain the reasoning** in an informative but not condescending way — this is an educational codebase.

## Architecture Overview

Java OOP prototype with no build tool (no Maven/Gradle). VS Code compiles from `src/` to `bin/` via the Java Language Server. Entry point: `pokemonGame.App.main()`.

**Core classes** (all in `src/pokemonGame/`):
- `Pokemon` — base class with Gen-III-style stat formulas (IVs, EVs, natures). Constructors are `protected`; species subclasses live in `pokemonGame.mons.*`.
- `Move` — `abstract` base; each move is a concrete subclass in `pokemonGame.moves.*` with hardcoded stats passed to `super(name, power, type, category, accuracy, pp)`.
- `MoveSlot` — wraps a `Move` with mutable PP tracking. Pokémon movesets are `ArrayList<MoveSlot>` (max 4).
- `Attack` — stateless damage calculator. Uses `TypeChart` for effectiveness and implements STAB, crits, and a random factor.
- `TypeChart` — 18×18 float matrix indexed by string type names via a static `Map<String, Integer>`.
- `Natures` — enum of 25 natures; each stores its boosted/decreased `Stat` and exposes `modifierFor(Stat)`.
- `Stat` — simple enum: `HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED`.
- `LearnsetEntry` — associates a `Move` with a learning source (`LEVEL`, `TM`, etc.) and parameter (level number). Static `teachFromLearnset()` drives interactive move learning.
- `Trainer` — holds a name and `ArrayList<Pokemon>` team (max 6).

## Adding a New Pokémon Species

1. Create `src/pokemonGame/mons/NewSpecies.java` extending `Pokemon`.
2. Declare a `private static final List<LearnsetEntry> LEARNSET` populated in a `static {}` block.
3. Constructor signature: `public NewSpecies(String name)` calling `super(species, dexIndex, type1, type2, level, hp, atk, def, spAtk, spDef, spd)`. **Default level is 5** for all species; use `setLevel()` after construction if a different level is needed.
4. In the constructor body: `setName(name)`, `setEvYield(...)`, `generateRandomIVs()`, `calculateCurrentStats()` — **in that order**.
5. Override `getLearnset()` to return `LEARNSET`.
6. Register the species in `App.createPokemon()` switch statement.

Only **Gen 1 Pokémon** (Kanto dex #1–151) should be added. Example pattern — see `Bulbasaur.java`.

## Adding a New Move

Create `src/pokemonGame/moves/NewMove.java` extending `Move`:
```java
public class NewMove extends Move {
    public NewMove() {
        super("Move Name", power, "Type", "Physical|Special|Status", accuracy, pp);
    }
}
```
Move categories must be exactly `"Physical"`, `"Special"`, or `"Status"` — these strings drive which attack/defense stats are used in `Pokemon.getAttackStatForMove()`. Only Gen 1 moves should be added.

## Input / Interactivity Pattern

This project may be adapted for a Discord bot, so the game logic must stay **decoupled from I/O**. The recommended pattern:

- **Game-logic methods** (damage calculation, stat changes, move learning eligibility) should be pure — accept parameters, return results, never read from `Scanner`/`System.console()` directly.
- **I/O lives in a thin controller layer** (currently `App`). When input is needed, the controller collects it and passes it into game-logic methods.
- Avoid holding a `Scanner` open across method boundaries. Create one `Scanner` at the top of the input loop, use it for all prompts in that interaction, and close it when the interaction is done — or pass a `Scanner` into methods that need it rather than constructing new ones internally.
- `Pokemon.addMove()` and `LearnsetEntry.teachFromLearnset()` currently violate this by reading input internally — these should eventually be refactored so the UI choice is made externally and the result is passed in.

## Key Conventions

- **Types are strings**, not enums. Use exact capitalized names matching `TypeChart.TYPE_INDICES` keys: `"Normal"`, `"Fire"`, `"Water"`, `"Electric"`, `"Grass"`, `"Ice"`, `"Fighting"`, `"Poison"`, `"Ground"`, `"Flying"`, `"Psychic"`, `"Bug"`, `"Rock"`, `"Ghost"`, `"Dragon"`, `"Dark"`, `"Steel"`, `"Fairy"`.
- Secondary type is `null` (not `"None"`) for single-type Pokémon. `Attack.calculateEffectiveness` treats `null` as neutral.
- **Default level is 5** for all newly constructed Pokémon. `setLevel()` exists for creating Pokémon at other levels after construction.
- Stat calculation follows the mainline formula: `((2*base + IV + EV/4) * level / 100 + 5) * natureModifier`. HP uses a different formula (see `Pokemon.calcMaxHP`).
- EV setters are **additive** (they add to the current value) and enforce per-stat max 252 / total max 510.
- `Pokemon` constructors call `Natures.assignRandom(this)` automatically — do not assign natures manually in species constructors.
- The project has **no test framework** currently. Manual testing happens in `App.main()`.

## Build & Run

No Maven/Gradle. Compile and run via VS Code's Java extension or:
```
javac -d bin src/pokemonGame/*.java src/pokemonGame/mons/*.java src/pokemonGame/moves/*.java
java -cp bin pokemonGame.App
```

## Creating files should be added in the appropriate package subdirectory under `src/pokemonGame/`:
- New Pokémon species: `src/pokemonGame/mons/`
- New moves: `src/pokemonGame/moves/`
- Core classes (if needed): `src/pokemonGame/`
When editing existing files, maintain the existing package structure and naming conventions. Back up existing files before making significant changes, especially to core classes like `Pokemon` and `Attack`, as these are widely used across the codebase. When adding new Pokémon or moves, ensure that they are registered in the appropriate places (e.g., new species in `App.createPokemon()`) to be accessible in the game. Follow the established patterns for constructors and method implementations to maintain consistency across the codebase.

## Work-in-Progress Areas

- `App.selectFromAvailablePokemon()` is stubbed out (returns `null`). `createPokemon()` only handles 3 species; new species need to be added to its switch.
- Battle loop, turn ordering, and HP reduction are not yet implemented — `Attack.calculateDamage` returns damage but nothing applies it.
- Input handling mixes `Scanner` and `System.console()` — needs refactoring toward the controller pattern described above.
- **Status effects** (burn, paralysis, poison, sleep, freeze) are planned for much later. Items and abilities are not in scope.
- `currentHP` field exists on `Pokemon` but is never decremented — the battle damage application loop is not yet written.
