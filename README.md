# Pokémon OOP — A Text-Based Pokémon Red Reimagining

A Java 21 learning project that reimplements Pokémon Red as a text-based Discord bot, built from the ground up using Object-Oriented Programming principles. Features all **151 Gen 1 Pokémon** and **165 Gen 1 moves** with modernized mechanics inspired by later generations.

---

## Overview

This project is an educational OOP codebase that models the core systems of a Pokémon game, delivered as a **Discord bot** using **JDA (Java Discord API)**:

- **All 151 Kanto Pokémon** fully implemented with accurate base stats, types, learnsets, and EV yields
- **All 165 Gen 1 moves** implemented (Physical, Special, and Status categories)
- **Full learnsets** — all species have complete level-up, TM, and HM learnsets sourced from PokémonDB
- **Modernized stat system** — Special is split into Sp.Atk / Sp.Def (Gen III+), IVs range 0–31, EVs use the modern 0–252 per stat / 510 total system
- **25 Natures** with ±10% stat modifiers
- **Full type chart** covering all 18 types with correct effectiveness multipliers
- **Damage calculation** with STAB, type effectiveness, critical hits, and random variance
- **Battle turn system** — sealed interface `BattleAction` with `MoveAction` and `SwitchAction` records; `TurnManager` resolves turns with speed-based priority
- **Discord bot** — slash commands for creating trainers, managing teams, adding/releasing Pokémon, initiating battles
- **Database persistence** — MariaDB with HikariCP connection pooling for trainers, teams, Pokémon instances, battle state, and turn history
- **Service layer** — `TrainerService`, `TeamService`, `BattleService` coordinate between bot and persistence layers

### What Makes This Different from Mainline Games

| Mechanic | Pokémon Red (Gen I) | This Project |
| ---------- | --------------------- | ------------- |
| Special stat | Single "Special" stat | Split into Sp.Atk and Sp.Def |
| IVs | 0–15 (DVs) | 0–31 (modern system) |
| EVs | Uncapped (gained from base stats) | 0–252 per stat, 510 total |
| Natures | Did not exist | 25 natures with stat modifiers |
| Critical hits | Based on Speed/512 | Custom speed-differential formula (see `Attack.calculateCriticalHit`) |
| Type chart | 15 types | 18 types (includes Dark, Steel, Fairy) |

---

## Project Structure

```tree
src/main/java/pokemonGame/
├── battle/                # Battle mechanics (Attack, TurnManager, BattleAction)
│   ├── Attack.java        # Stateless damage calculator (STAB, crits, effectiveness)
│   ├── BattleAction.java  # Sealed interface: MoveAction | SwitchAction
│   ├── MoveAction.java    # Record: move choice for a turn
│   ├── SwitchAction.java  # Record: switch choice for a turn
│   ├── TurnManager.java   # Resolves a full turn (speed priority, damage, faint check)
│   ├── TurnResult.java    # Record: outcome of a turn
│   └── DamageResult.java  # Record: outcome of a single attack
├── bot/                   # Discord bot (JDA event listeners)
│   ├── BotRunner.java     # Entry point: builds JDA, registers slash commands
│   ├── SlashExample.java  # Slash command handler (createtrainer, addpokemon, etc.)
│   └── AutoCompleteBot.java # Autocomplete suggestions for species/pokemon/teams
├── core/                  # Shared utilities and game constants
│   ├── EvManager.java     # EV add/set/cap enforcement (252 per stat, 510 total)
│   ├── Natures.java       # 25 natures enum with stat modifiers
│   ├── Stat.java          # Enum: HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED
│   ├── StatCalculator.java# Gen III+ stat formulas (calcMaxHP, calcCurrentStat)
│   └── TypeChart.java     # 18×18 effectiveness matrix + Type/Category/StatusCondition enums
├── db/                    # Persistence layer (JDBC + MariaDB via HikariCP)
│   ├── DatabaseSetup.java # Connection pool config, deleteAllData() with table whitelist
│   ├── TrainerCRUD.java   # CRUD for trainers table
│   ├── PokemonCRUD.java   # CRUD for pokemon_instances table
│   ├── TeamCRUD.java      # CRUD for trainer_teams join table
│   ├── BattleCRUD.java    # CRUD for battles table
│   └── BattleTurnCRUD.java# Pending actions + turn history persistence
├── model/                 # Domain model (pure game logic, no I/O)
│   ├── Pokemon.java       # Base class: stats, IVs, EVs, natures, movesets (173 importers)
│   ├── Move.java          # Abstract base for all moves (172 importers)
│   ├── MoveSlot.java      # Wraps a Move with mutable PP tracking
│   ├── LearnsetEntry.java # Associates moves with learning sources (LEVEL, TM, HM)
│   ├── Trainer.java       # Holds name, Discord ID, DB ID, and Team
│   ├── Team.java          # Team of up to 6 Pokémon with slot management
│   └── Battle.java        # Battle state: trainer IDs, active Pokémon, status, timestamps
├── service/               # Business logic coordination
│   ├── TrainerService.java# Trainer creation, lookup, validation
│   ├── TeamService.java   # Team creation, add/release Pokémon, slot management
│   └── BattleService.java # Battle creation, challenge flow, faint checking
├── species/               # 151 Pokémon species subclasses
│   ├── PokeSpecies.java   # Enum registry of all species with aliases
│   ├── PokemonFactory.java# Static factory: name → Pokemon instance
│   ├── Abra.java          # Species: base stats, type, learnset
│   ├── ...                # (151 species files total)
│   └── Zubat.java
└── moves/                 # 165 Gen 1 move subclasses
    ├── Absorb.java        # Move: name, power, type, category, accuracy, PP
    ├── ...                # (165 move files total)
    └── WingAttack.java
```

---

## Build & Run

### Prerequisites

- **Java JDK 21** or higher
- **Maven 3.8+**
- **MariaDB** running with the `pokemon_db` database created (see `Documentation/Database-Documentation.md`)
- **Environment variables** set for database and bot token:
  - `DB_URL` — JDBC URL (e.g., `jdbc:mariadb://localhost:3306/pokemon_db`)
  - `DB_USER` — Database username
  - `DB_USER_PASSWORD` — Database password
  - `MOKEPONS_API_KEY` — Discord bot token

### Using Maven

```bash
# Compile
mvn compile

# Run tests (requires DB connection)
mvn test

# Build fat JAR (with all dependencies)
mvn clean package

# Run the Discord bot
mvn exec:java -Dexec.mainClass="pokemonGame.bot.BotRunner"

# Run the local App class (for testing/setup)
mvn exec:java -Dexec.mainClass="pokemonGame.App"
```

**Entry point (bot):** `pokemonGame.bot.BotRunner.main()`

---

## How It Works

### Architecture

The project follows a **layered architecture** with strict separation of concerns:

```tree
┌───────────────────────────────────┐
│  Bot Layer (JDA event listeners)  │  Parses Discord input, formats replies
├───────────────────────────────────┤
│  Service Layer                    │  Coordinates business logic across DAOs
├───────────────────────────────────┤
│  Domain / Model Layer             │  Pure game logic (stats, damage, types)
├───────────────────────────────────┤
│  Persistence / DB Layer           │  JDBC + HikariCP → MariaDB
└───────────────────────────────────┘
```

- **Model classes** (`Pokemon`, `Move`, `TypeChart`, `Attack`) are pure logic — no I/O, no Discord, no database imports.
- **Service layer** (`TrainerService`, `TeamService`, `BattleService`) coordinates multi-step operations and enforces business rules.
- **Bot layer** (`SlashExample`, `AutoCompleteBot`) handles Discord I/O and delegates to services.
- **DB layer** (`TrainerCRUD`, `PokemonCRUD`, `TeamCRUD`, `BattleCRUD`) handles SQL via prepared statements.

### Battle System

The battle system uses Java 21's sealed interfaces and records:

```java
sealed interface BattleAction permits MoveAction, SwitchAction { ... }
record MoveAction(Trainer, Pokemon, Team, int moveSlotIndex) implements BattleAction { ... }
record SwitchAction(Trainer, Pokemon, Team, int teamSlotIndex) implements BattleAction { ... }
```

`TurnManager.resolveTurn()` determines turn order (speed priority, switches first), resolves damage, checks for fainting, and returns an immutable `TurnResult` record.

### Stat Calculation

Stats follow the mainline Gen III+ formula:

$$\text{Stat} = \left\lfloor\left(\frac{(2 \times \text{Base} + \text{IV} + \lfloor\text{EV}/4\rfloor) \times \text{Level}}{100} + 5\right) \times \text{NatureModifier}\right\rfloor$$

HP uses a variant formula (see `StatCalculator.calcMaxHP`).

### Damage Calculation

`Attack.calculateDamage()` implements:

1. **STAB** — 1.5× bonus when the move type matches the attacker's type
2. **Type effectiveness** — looked up from the 18×18 `TypeChart` matrix (supports dual types)
3. **Critical hits** — custom speed-differential formula: base ~4% chance, scaling with speed advantage, capped at ~20%
4. **Random variance** — damage multiplied by a random factor (217–255 / 255)

### Database

MariaDB stores persistent game data across these tables:

- `trainers` — trainer profiles with Discord IDs
- `trainer_teams` — join table linking trainers to team slots
- `pokemon_instances` — individual Pokémon with stats, IVs, EVs, nicknames
- `pokemon_movesets` — movesets for each Pokémon instance
- `battles` — active and historical battle records
- `battle_pending_actions` — queued actions for the current turn
- `battle_turn_history` — turn-by-turn summaries

See `Documentation/Database-Documentation.md` for schema details.

### Discord Bot Commands

| Command | Description |
| --------- | ------------- |
| `/createtrainer` | Register a new trainer profile |
| `/addpokemon` | Add a Pokémon to your team (with species autocomplete) |
| `/checkteam` | View your team's Pokémon and stats |
| `/releasepokemon` | Release a Pokémon from your team |
| `/battlestate` | Initiate or check battle status |
| `/cleardatabase` | Admin: reset all data (requires ADMINISTRATOR permission) |
| `/say` | Echo a message |
| `/ping` | Bot health check |

---

## Adding Content

### Adding a New Pokémon Species

1. Create `src/main/java/pokemonGame/species/NewSpecies.java` extending `Pokemon`
2. Define a `private static final List<LearnsetEntry> LEARNSET` in a `static {}` block
3. Constructor calls `super(PokeSpecies.SPECIES, dexIndex, type1, type2, level, hp, atk, def, spAtk, spDef, spd)` — **default level is 5**
4. Constructor body (in order): `setName(name)`, `setEvYield(...)`, `generateRandomIVs()`, `calculateCurrentStats()`
5. Override `getLearnset()` to return `LEARNSET`
6. Add the species to the `PokeSpecies` enum and register in `PokemonFactory`

### Adding a New Move

Create `src/main/java/pokemonGame/moves/NewMove.java` extending `Move`:

```java
public class NewMove extends Move {
    public NewMove() {
        super("Move Name", power, Type.FIRE, Category.PHYSICAL, accuracy, pp);
    }
}
```

Move categories must be `Category.PHYSICAL`, `Category.SPECIAL`, or `Category.STATUS`.

> **Scope:** Only Gen 1 Pokémon (Kanto #1–151) and Gen 1 moves.

---

## Testing

```bash
mvn test
```

**447 tests** across 9 implemented test suites:

| Suite | Tests | Coverage |
| ------- | ------- | ---------- |
| TypeChartTest | ~200+ parameterized | All 18 type matchups |
| AttackTest | ~50 | Accuracy, crits, damage formula |
| TurnManagerTest | ~40 | Turn order, fainting, switching, PP |
| PokemonTest | ~40 | Stats, IVs, EVs, moves, leveling |
| NaturesTest | ~30 | All 25 natures, modifiers |
| LearnsetEntryTest | ~25 | Eligibility filtering, sources |
| EvSetterTest | ~25 | EV setter caps, totals |
| EvAdderTest | ~25 | EV adder caps, totals |
| TrainerCRUDTest | ~10 | DB round-trip CRUD |

DB tests require a running MariaDB instance with the `pokemon_db` database.

---

## Current Status

### Implemented

- [x] All 151 Gen 1 Pokémon with base stats, types, learnsets, and EV yields
- [x] All 165 Gen 1 moves across Physical, Special, and Status categories
- [x] Full stat system (IVs, EVs, natures, level-up stat recalculation)
- [x] Damage calculation with STAB, type effectiveness, crits, and variance
- [x] 18-type effectiveness chart
- [x] Battle turn system with sealed interfaces, records, and speed-based priority
- [x] Discord bot with slash commands (JDA 6.3.1)
- [x] MariaDB persistence with HikariCP connection pooling
- [x] Service layer (TrainerService, TeamService, BattleService)
- [x] Trainer creation, Pokémon addition/release via Discord
- [x] PP tracking per move slot
- [x] All 151 species registered in `PokeSpecies` enum + `PokemonFactory`
- [x] Maven build with shade plugin for fat JAR
- [x] 447 passing tests

### Work in Progress

- [ ] **Full battle loop integration** — TurnManager works, but the Discord-to-battle pipeline needs completion (action submission via JDA buttons/menus)
- [ ] **Status move effects** — Status and secondary effects are not yet applied, only damage-dealing moves
- [ ] **`BattleService.createActionFromDbRow()`** — stub, needs implementation for reconstructing actions from DB
- [ ] **`BattleCRUD` stubs** — `getBattleHistoryForTrainer()` and `getAllActiveBattles()` not yet implemented
- [ ] **Test coverage gaps** — 13 of 22 test files are empty (MoveSlot, Team, PokemonCRUD, TeamCRUD, services, species, bot)

### Planned (Future)

- [ ] Status effects (burn, paralysis, poison, sleep, freeze) — `StatusCondition` enum already in `TypeChart`
- [ ] Stat stage changes (+1 Attack, -1 Defense, etc.)
- [ ] Wild Pokémon encounters
- [ ] Trainer battles with AI
- [ ] Evolution
- [ ] Experience gain tracking and level-up flow

### Out of Scope

- Items and abilities
- Pokémon beyond Gen 1
- Moves beyond Gen 1

---

## License

This is an educational project. Pokémon is a trademark of Nintendo / Game Freak / The Pokémon Company. This project is not affiliated with or endorsed by any of those entities.
