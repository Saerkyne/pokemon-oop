# Copilot Instructions — Pokemon OOP

## Project Context

This is a Java 21 project using Maven, JDA 6.3.1 (Discord bot), MariaDB (via HikariCP), and SLF4J + Logback.

High-impact files (most imported, changes here affect many other files):
- src/main/java/pokemonGame/model/Pokemon.java (imported by 173 files)
- src/main/java/pokemonGame/model/Move.java (imported by 172 files)
- src/main/java/pokemonGame/core/StatCalculator.java (imported by 157 files)
- src/main/java/pokemonGame/core/Stat.java (imported by 157 files)
- src/main/java/pokemonGame/model/LearnsetEntry.java (imported by 152 files)
- src/main/java/pokemonGame/model/Trainer.java (imported by 13 files)
- src/main/java/pokemonGame/model/Team.java (imported by 9 files)
- src/main/java/pokemonGame/species/Abra.java (imported by 7 files)

Read .codesight/wiki/index.md for orientation (WHERE things live). Then read actual source files before implementing. Wiki articles are navigation aids, not implementation guides.
Read .codesight/CODESIGHT.md for the complete AI context map including all routes, schema, components, libraries, config, middleware, and dependency graph.

## Project Purpose

This is a **learning project** for Java and Object-Oriented Programming concepts. It is a modified, text-based re-implementation of Pokémon Red, delivered as a **Discord bot** using **JDA (Java Discord API)**. Only **Gen 1 Pokémon and moves** (original 151 + their moves) are in scope, but several mechanics are modernized: the Special stat is split into Sp.Atk/Sp.Def, IVs and EVs use the modern 0-31 / 0-252 system, natures exist, and the critical-hit formula is custom (speed-differential-based, see `Attack.calculateCriticalHit`). When suggesting changes, **explain the reasoning** in an informative but not condescending way — this is an educational codebase.

## Requested Conventions
- Follow Java best practices for naming, encapsulation, and code organization.
- Maintain the layered architecture (model, battle, core, service, persistence/db, bot) to keep concerns separated.
- Use JDA's event system for all user interactions — no direct console input.
- When making suggestions, explain the rationale behind them, especially if they involve design patterns or architectural principles. The goal is to learn and understand, not just to get a working implementation.
- **Make suggestions inside comments in the file being referenced** rather than just describing them in messages. This way, the reasoning is directly tied to the relevant code sections for better learning.
- **Always place code examples and snippets inside Javadoc or block comments in the relevant source file** in addition to showing them in chat messages. In chat, reference the file and line numbers where the examples were added (e.g., "See BattleAction.java lines 18–52 for examples"). This works around VS Code chat rendering issues with multiple code fences and keeps examples permanently tied to the code they explain.

## Technology Stack

- **Java 21** — core language. The project uses **Maven** as its build tool (`pom.xml` at the project root), following the standard Maven directory layout (`src/main/java/`, `src/test/java/`, `src/main/resources/`). Maven manages all dependencies, compilation, testing, and packaging (including a shade plugin for fat JARs).
  - **Best practice:** Always declare dependencies in `pom.xml` with explicit version numbers so builds are reproducible. Use `<scope>test</scope>` for test-only dependencies like JUnit. Adding a new library means adding a `<dependency>` block to `pom.xml` — never drop JARs into the project manually.
- **JDA (Java Discord API) 6.3.1** — the Discord bot framework used to interact with users. JDA manages the WebSocket connection to Discord, dispatches events (slash commands, button clicks, autocomplete) to registered listener classes, and provides builders for sending messages, embeds, and interactive components.
  - **Best practice:** Register slash commands declaratively in `BotRunner`, use `@Override` on listener methods, and prefer ephemeral replies for error messages.
  - **Best practice:** Keep JDA event handlers thin — parse Discord input, delegate to service-layer methods, format the response. This makes game logic testable without a live Discord connection.
  - **Best practice:** Always null-check `event.getOption()` results before calling `.getAsString()` or `.getAsUser()`. JDA returns `null` for optional parameters not provided.
- **MariaDB** — relational database storing persistent game data (trainer profiles, owned Pokémon, team composition, battle state, turn history). Accessed via JDBC with **HikariCP** connection pooling (configured in `DatabaseSetup.java`).
  - **Best practice:** Use **prepared statements** for all database queries to prevent SQL injection. Never concatenate user input directly into SQL strings.
  - **Best practice:** Use **try-with-resources** for `Connection`, `PreparedStatement`, and `ResultSet` objects.
  - **Best practice:** Database credentials are read from environment variables (`DB_URL`, `DB_USER`, `DB_USER_PASSWORD`) or system properties. Never hard-code credentials.
- **SLF4J + Logback** — logging framework. Configuration lives in `src/main/resources/logback.xml`.
  - **Best practice:** Use parameterized logging (`logger.info("Processing {}", value)`) rather than string concatenation.
- **JUnit 5 (Jupiter)** — testing framework. Tests live in `src/test/java/pokemonGame/` and are run via `mvn test`. Currently 447 tests pass across 9 implemented test suites (of 22 total files).
  - **Best practice:** Write tests for domain-logic classes first. Since they have no I/O dependencies, they are straightforward to test.

## Architecture Overview

Entry point: `pokemonGame.bot.BotRunner.main()` launches the Discord bot.

### Layers

1. **Model Layer** (`pokemonGame.model.*`) — Pure game data. No I/O, no Discord, no database.
   - `Pokemon` — base class with stats, IVs, EVs, natures, movesets. Constructors `protected`; species subclasses in `pokemonGame.species.*`.
   - `Move` — `abstract` base; each move is a concrete subclass in `pokemonGame.moves.*`.
   - `MoveSlot` — wraps a `Move` with mutable PP tracking.
   - `LearnsetEntry` — associates a `Move` with a learning source (`LEVEL`, `TM`, `HM`, etc.).
   - `Trainer` — holds name, Discord ID, database ID, and a `Team`.
   - `Team` — up to 6 Pokémon with slot management.
   - `Battle` — battle state (trainer IDs, active Pokémon IDs, status enum, timestamps).

2. **Battle Layer** (`pokemonGame.battle.*`) — Turn resolution and damage calculation.
   - `BattleAction` — `sealed interface` permitting `MoveAction` and `SwitchAction` (records).
   - `TurnManager` — resolves a turn: speed priority, damage application, faint checking.
   - `Attack` — stateless damage calculator (STAB, crits, type effectiveness, random factor).
   - `TurnResult` / `DamageResult` — immutable records capturing turn outcomes.

3. **Core Layer** (`pokemonGame.core.*`) — Shared constants and utilities.
   - `TypeChart` — 18×18 effectiveness matrix + `Type`, `Category`, `StatusCondition` enums.
   - `StatCalculator` — Gen III+ stat formulas.
   - `Natures` — 25 natures enum with stat modifiers.
   - `EvManager` — EV add/set with per-stat (252) and total (510) cap enforcement.
   - `Stat` — enum: HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED.

4. **Service Layer** (`pokemonGame.service.*`) — Business logic coordination.
   - `TrainerService` — trainer creation, duplicate checking, lookup.
   - `TeamService` — team creation, add/release Pokémon, slot management.
   - `BattleService` — battle creation, challenge flow, faint checking.

5. **Persistence Layer** (`pokemonGame.db.*`) — JDBC + HikariCP.
   - `DatabaseSetup` — connection pool config, `deleteAllData()` with table whitelist.
   - `TrainerCRUD`, `PokemonCRUD`, `TeamCRUD` — CRUD for game entities.
   - `BattleCRUD`, `BattleTurnCRUD` — battle and turn persistence.

6. **Bot Layer** (`pokemonGame.bot.*`) — Discord-facing I/O.
   - `BotRunner` — builds JDA, registers slash commands.
   - `SlashExample` — slash command handler (delegates to services).
   - `AutoCompleteBot` — autocomplete suggestions for species, Pokémon, teams.

### Data Flow

```
Discord User → Bot Layer → Service Layer → Model/Battle Logic
                                         → Persistence Layer → MariaDB
```

## Adding a New Pokémon Species

All 151 Gen 1 Pokémon are already implemented. If a species needs to be re-created or the pattern understood:

1. Create `src/main/java/pokemonGame/species/NewSpecies.java` extending `Pokemon`.
2. Declare a `private static final List<LearnsetEntry> LEARNSET` populated in a `static {}` block.
3. Constructor signature: `public NewSpecies(String name)` calling `super(PokeSpecies.SPECIES, dexIndex, type1, type2, level, hp, atk, def, spAtk, spDef, spd)`. **Default level is 5** for all species.
4. In the constructor body: `setName(name)`, `setEvYield(...)`, `generateRandomIVs()`, `calculateCurrentStats()` — **in that order**.
5. Override `getLearnset()` to return `LEARNSET`.
6. Add the species to the `PokeSpecies` enum and register in `PokemonFactory`.

Only **Gen 1 Pokémon** (Kanto dex #1–151) should be added. Example pattern — see `Abra.java`.

## Adding a New Move

Create `src/main/java/pokemonGame/moves/NewMove.java` extending `Move`:
```java
public class NewMove extends Move {
    public NewMove() {
        super("Move Name", power, Type.FIRE, Category.PHYSICAL, accuracy, pp);
    }
}
```
Move categories must be `Category.PHYSICAL`, `Category.SPECIAL`, or `Category.STATUS` — these enums drive which attack/defense stats are used in `Pokemon.getAttackStatForMove()`. Only Gen 1 moves should be added.

## Input / Interactivity Pattern

Since this project runs as a **Discord bot via JDA**, all user interaction flows through Discord events (slash commands, button clicks, select menus). The guiding principle is **separation of I/O from logic**:

- **Game-logic methods** (damage calculation, stat changes, move learning eligibility) should be pure — accept parameters, return results, never touch JDA, `Scanner`, or `System.console()` directly.
- **I/O lives in the bot/controller layer.** JDA event listeners collect user input from Discord, pass it into service-layer methods, and send results back as Discord messages or embeds.
- **Best practice:** Use JDA's `SlashCommandInteractionEvent`, `ButtonInteractionEvent`, etc. to gather user choices. Acknowledge interactions promptly (within 3 seconds) using `deferReply()` if processing takes time.
- **Best practice:** For multi-step interactions (e.g., choosing which move to forget), use JDA's **button** or **select menu** components rather than waiting for freeform text messages.
- `MoveSlot.teachMoveFromLearnset()` still contains a legacy `System.console()` call that should be removed — move teaching should be handled via Discord components.

## Database (MariaDB) Conventions

- All persistent game state is stored in **MariaDB** via JDBC prepared statements.
- Connection pooling is handled by **HikariCP** (configured in `DatabaseSetup.java`).
- Database credentials come from environment variables: `DB_URL`, `DB_USER`, `DB_USER_PASSWORD`.
- Tables: `trainers`, `trainer_teams`, `pokemon_instances`, `pokemon_movesets`, `battles`, `battle_pending_actions`, `battle_turn_history`.
- **Best practice:** Use `try-with-resources` for `Connection`, `PreparedStatement`, and `ResultSet`.
- **Best practice:** Keep CRUD classes focused on SQL. Business logic belongs in the service layer.
- Schema documentation: see `Documentation/Database-Documentation.md`.

## Key Conventions

- **Types use the `Type` enum** from `TypeChart.java`: `Type.NORMAL`, `Type.FIRE`, `Type.WATER`, etc. Secondary type is `null` for single-type Pokémon.
- **Move categories use the `Category` enum**: `Category.PHYSICAL`, `Category.SPECIAL`, `Category.STATUS`.
- **Default level is 5** for all newly constructed Pokémon. `setLevel()` exists for creating Pokémon at other levels after construction.
- Stat calculation follows the mainline formula: `((2*base + IV + EV/4) * level / 100 + 5) * natureModifier`. HP has a different formula (see `StatCalculator.calcMaxHP`).
- EV setters are managed through `EvManager` and enforce per-stat max 252 / total max 510.
- `Pokemon` constructors call `Natures.assignRandom(this)` automatically — do not assign natures manually in species constructors.
- **Naming:** `PascalCase` for classes, `camelCase` for methods and variables, `UPPER_SNAKE_CASE` for constants.
- **Encapsulation:** Prefer `private` fields with getters/setters. Return defensive copies for mutable collections (`Collections.unmodifiableList()`).
- **Battle actions** use Java 21 sealed interfaces + records: `BattleAction` → `MoveAction | SwitchAction`.

## Build & Run

```bash
mvn compile          # Compile main sources
mvn test             # Run JUnit 5 tests (447 tests, requires DB)
mvn clean package    # Build fat JAR via shade plugin
mvn exec:java -Dexec.mainClass="pokemonGame.bot.BotRunner"  # Run bot
```

The `MOKEPONS_API_KEY` environment variable must be set with the Discord bot token before running `BotRunner`.

## File Organization

```
src/
  main/
    java/pokemonGame/
      battle/       # Battle mechanics (Attack, TurnManager, BattleAction, records)
      bot/          # JDA event listeners and bot setup
      core/         # Shared utilities (TypeChart, StatCalculator, Natures, EvManager, Stat)
      db/           # Database/DAO classes (JDBC + HikariCP + MariaDB)
      model/        # Domain model (Pokemon, Move, MoveSlot, LearnsetEntry, Trainer, Team, Battle)
      service/      # Business logic (TrainerService, TeamService, BattleService)
      species/      # 151 species subclasses + PokeSpecies enum + PokemonFactory
      moves/        # 165 move subclasses
    resources/      # logback.xml
  test/
    java/pokemonGame/
      battleTests/  # AttackTest, TurnManagerTest
      botTests/     # (empty — bot layer hard to unit test without JDA mocking)
      coreTests/    # TypeChartTest, NaturesTest, EvSetterTest, EvAdderTest
      dbTests/      # TrainerCRUDTest (others empty)
      modelTests/   # PokemonTest, LearnsetEntryTest (MoveSlotTest, TeamTest empty)
      serviceTests/ # (all empty)
      speciesTests/ # (all empty)
```

## Known Issues (from Code Review 2026-04-08)

See `Documentation/Code-Review-2026-04-08.md` for the full review. Key items:

1. **Division by zero in `Attack.calculateDamage()`** — `defenseStat` can be 0, no guard.
2. **Crit formula bug** — doubling level doesn't produce 2× damage (non-linear formula).
3. **`MoveSlot.teachMoveFromLearnset()` uses `System.console()`** — legacy I/O in model layer.
4. **No bounds validation on `MoveAction`/`SwitchAction` index fields** — needs compact constructors.
5. **`SlashExample` god method** — 300+ line `onSlashCommandInteraction()` should be decomposed.
6. **Null-safety gaps in bot layer** — `event.getOption()` returns not null-checked.
7. **Team dual-storage** — `List<Pokemon>` and 6 named slot fields can desync.
8. **13 of 22 test files are empty** — see test coverage table in the review.

## Work-in-Progress Areas

- **Battle loop integration** — `TurnManager` works but the Discord-to-battle pipeline (action submission via JDA components) is incomplete.
- **Status move effects** — status moves exist as Move subclasses but their effects aren't applied.
- **`BattleService.createActionFromDbRow()`** — stub, needs implementation.
- **`BattleCRUD` stubs** — `getBattleHistoryForTrainer()` and `getAllActiveBattles()` return empty arrays.
- **Status effects** (burn, paralysis, poison, sleep, freeze) — `StatusCondition` enum exists, effects not implemented.
- Database schema and DAO layer may still evolve as features are added.
