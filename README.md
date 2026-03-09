# Pokémon OOP — A Text-Based Pokémon Red Reimagining

A Java learning project that reimplements Pokémon Red as a text-based game, built from the ground up using Object-Oriented Programming principles. Features all **151 Gen 1 Pokémon** with modernized mechanics inspired by later generations.

---

## Overview

This project is an educational OOP codebase that models the core systems of a Pokémon game:

- **All 151 Kanto Pokémon** fully implemented with accurate base stats, types, and learnsets
- **25 moves** implemented so far (Physical, Special, and Status categories)
- **Modernized stat system** — Special is split into Sp.Atk / Sp.Def (Gen III+), IVs range 0–31, EVs use the modern 0–252 per stat / 510 total system
- **25 Natures** with ±10% stat modifiers
- **Full type chart** covering all 18 types with correct effectiveness multipliers
- **Damage calculation** with STAB, type effectiveness, critical hits, and random variance
- **Move learning system** with learnsets, level-up moves, and TM compatibility

### What Makes This Different from Mainline Games

| Mechanic | Pokémon Red (Gen I) | This Project |
|----------|---------------------|-------------|
| Special stat | Single "Special" stat | Split into Sp.Atk and Sp.Def |
| IVs | 0–15 (DVs) | 0–31 (modern system) |
| EVs | Uncapped (gained from base stats) | 0–252 per stat, 510 total |
| Natures | Did not exist | 25 natures with stat modifiers |
| Critical hits | Based on Speed/512 | Custom speed-differential formula (see `Attack.calculateCriticalHit`) |
| Type chart | 15 types | 18 types (includes Dark, Steel, Fairy) |

---

## Project Structure

```
src/pokemonGame/
├── App.java              # Entry point & I/O controller
├── Pokemon.java          # Base class — stats, IVs, EVs, natures, movesets
├── Move.java             # Abstract base for all moves
├── MoveSlot.java         # Wraps a Move with mutable PP tracking
├── Attack.java           # Stateless damage calculator (STAB, crits, effectiveness)
├── TypeChart.java        # 18×18 effectiveness matrix
├── Natures.java          # Enum of 25 natures with stat modifiers
├── Stat.java             # Enum: HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED
├── LearnsetEntry.java    # Associates moves with learning sources (LEVEL, TM)
├── Trainer.java          # Holds a name and a team of up to 6 Pokémon
├── mons/                 # 151 species subclasses (Bulbasaur.java … Mew.java)
└── moves/                # 25 move subclasses (Tackle.java, Ember.java, etc.)
```

No build tool (Maven/Gradle) is used. The project compiles from `src/` to `bin/` via VS Code's Java Language Server.

---

## Build & Run

### Prerequisites

- **Java JDK** (8 or higher)
- **VS Code** with the [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) (recommended), or any Java IDE

### Using VS Code

Open the project folder in VS Code. The Java Language Server will automatically compile to `bin/`. Run `App.java` from the editor.

### Using the Command Line

```bash
# Compile
javac -d bin src/pokemonGame/*.java src/pokemonGame/mons/*.java src/pokemonGame/moves/*.java

# Run
java -cp bin pokemonGame.App
```

**Entry point:** `pokemonGame.App.main()`

---

## How It Works

### Core Architecture

The project follows a clean separation between **game logic** and **I/O**:

- **Domain classes** (`Pokemon`, `Attack`, `TypeChart`, `Move`) are pure logic — they accept parameters and return results without reading from `Scanner` or `System.console()`.
- **Controller layer** (`App`) handles all user interaction and passes choices into the domain classes.

This design keeps the game logic decoupled from I/O, making it adaptable (e.g., for a Discord bot frontend).

### Stat Calculation

Stats follow the mainline Gen III+ formula:

$$\text{Stat} = \left\lfloor\left(\frac{(2 \times \text{Base} + \text{IV} + \lfloor\text{EV}/4\rfloor) \times \text{Level}}{100} + 5\right) \times \text{NatureModifier}\right\rfloor$$

HP uses a variant formula (see `Pokemon.calcMaxHP`).

### Damage Calculation

`Attack.calculateDamage()` implements:
1. **STAB** — 1.5× bonus when the move type matches the attacker's type
2. **Type effectiveness** — looked up from the 18×18 `TypeChart` matrix (supports dual types)
3. **Critical hits** — custom speed-differential formula: base 4.17% chance, +0.83% per speed point the attacker is faster, capped at 15%
4. **Random variance** — damage is multiplied by a random factor (217–255 / 255)

---

## Adding Content

### Adding a New Pokémon Species

1. Create `src/pokemonGame/mons/NewSpecies.java` extending `Pokemon`
2. Define a `private static final List<LearnsetEntry> LEARNSET` in a `static {}` block
3. Constructor calls `super(species, dexIndex, type1, type2, level, hp, atk, def, spAtk, spDef, spd)` — **default level is 5**
4. Constructor body (in order): `setName(name)`, `setEvYield(...)`, `generateRandomIVs()`, `calculateCurrentStats()`
5. Override `getLearnset()` to return `LEARNSET`
6. Register in `App.createPokemon()` switch statement

### Adding a New Move

Create `src/pokemonGame/moves/NewMove.java` extending `Move`:

```java
public class NewMove extends Move {
    public NewMove() {
        super("Move Name", power, "Type", "Physical|Special|Status", accuracy, pp);
    }
}
```

Move categories must be exactly `"Physical"`, `"Special"`, or `"Status"`.

> **Scope:** Only Gen 1 Pokémon (Kanto #1–151) and Gen 1 moves should be added.

---

## Current Status

### Implemented

- [x] All 151 Gen 1 Pokémon with base stats, types
- [x] 25 moves across Physical, Special, and Status categories
- [x] Full stat system (IVs, EVs, natures, level-up stat recalculation)
- [x] Damage calculation with STAB, type effectiveness, crits, and variance
- [x] 18-type effectiveness chart
- [x] Move learning from learnsets (level-up and TM) with move replacement UI
- [x] Trainer class with 6-Pokémon team limit
- [x] PP tracking per move slot

### Work in Progress

- [ ] **Learnsets** — Learnsets will be hardcoded into Pokemon classes for each species
- [ ] **Battle loop** — damage is calculated but never applied to `currentHP`; turn ordering is not implemented
- [ ] **Special moves** — no special move effects are accounted for currently, only damage dealing
- [ ] **Pokémon selection UI** — `selectFromAvailablePokemon()` is stubbed out
- [ ] **More moves** — only 25 of ~165 Gen 1 moves are implemented so far
- [ ] **Input refactoring** — some input handling mixes `Scanner` and `System.console()`

### Planned (Future)

- [ ] Status effects (burn, paralysis, poison, sleep, freeze)
- [ ] Stat stage changes (+1 Attack, -1 Defense, etc.)
- [ ] Wild Pokémon encounters
- [ ] Trainer battles with AI
- [ ] Evolution
- [ ] Gender

### Out of Scope

- Items and abilities
- Pokémon beyond Gen 1
- Moves beyond Gen 1

---

## License

This is an educational project. Pokémon is a trademark of Nintendo / Game Freak / The Pokémon Company. This project is not affiliated with or endorsed by any of those entities.
