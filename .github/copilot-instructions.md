# Copilot Instructions — Pokemon OOP

## Project Purpose

This is a **learning project** for Java and Object-Oriented Programming concepts. It is a modified, text-based re-implementation of Pokémon Red, delivered as a **Discord bot** using **JDA (Java Discord API)**. Only **Gen 1 Pokémon and moves** (original 151 + their moves) are in scope, but several mechanics are modernized: the Special stat is split into Sp.Atk/Sp.Def, IVs and EVs use the modern 0-31 / 0-252 system, natures exist, and the critical-hit formula is custom (speed-differential-based, see `Attack.calculateCriticalHit`). When suggesting changes, **explain the reasoning** in an informative but not condescending way — this is an educational codebase.

## Requested Conventions
- Follow Java best practices for naming, encapsulation, and code organization.
- Maintain the layered architecture (domain/model, persistence/DAO, controller/bot) to keep concerns separated.
- Use JDA's event system for all user interactions — no direct console input.
- When making suggestions, explain the rationale behind them, especially if they involve design patterns or architectural principles. The goal is to learn and understand, not just to get a working implementation. 
- **Make suggestions inside comments in the file being referenced** rather than just describing them in messages. This way, the reasoning is directly tied to the relevant code sections for better learning.
- **Always place code examples and snippets inside Javadoc or block comments in the relevant source file** rather than only showing them in chat messages. In chat, reference the file and line numbers where the examples were added (e.g., "See BattleAction.java lines 18–52 for examples"). This works around VS Code chat rendering issues with multiple code fences and keeps examples permanently tied to the code they explain.

## Technology Stack

- **Java 21** — core language. The project uses **Maven** as its build tool (`pom.xml` at the project root), following the standard Maven directory layout (`src/main/java/`, `src/test/java/`, `src/main/resources/`). Maven manages all dependencies, compilation, testing, and packaging.
  - **Best practice:** Always declare dependencies in `pom.xml` with explicit version numbers so builds are reproducible. Use `<scope>test</scope>` for test-only dependencies like JUnit. Adding a new library means adding a `<dependency>` block to `pom.xml` — never drop JARs into the project manually.
- **JDA (Java Discord API)** — the Discord bot framework (currently **JDA 6.3.1**) used to interact with users. JDA is a Java library that connects to the Discord API, handling event listening, slash commands, message embeds, button interactions, and autocomplete. All Discord-facing I/O should go through JDA's API; never use `Scanner` or `System.console()` in production paths.
  - **What JDA does:** JDA manages the WebSocket connection to Discord, dispatches events (like slash command invocations or button clicks) to your registered listener classes, and provides builders for sending messages, embeds, and interactive components (buttons, select menus) back to Discord channels.
  - **Best practice:** Register slash commands declaratively in `BotRunner`, use `@Override` on listener methods, and prefer ephemeral replies for error messages to keep channels clean.
  - **Best practice:** Keep JDA event handlers thin — they should parse Discord input, delegate to game-logic methods, and format the response. This makes the game logic testable without a live Discord connection.
- **MariaDB** — the relational database storing persistent game data (trainer profiles, owned Pokémon, team composition, etc.). MariaDB is an open-source relational database that uses SQL for data manipulation. It is accessed from Java via **JDBC** (Java Database Connectivity), the standard Java API for executing SQL statements against a database. The MariaDB JDBC driver (declared in `pom.xml`) translates JDBC calls into the MariaDB wire protocol.
  - **Best practice:** Use **prepared statements** for all database queries to prevent SQL injection. Prepared statements separate SQL structure from data — the database driver handles escaping, so user input can never be interpreted as SQL code. Never concatenate user input directly into SQL strings.
  - **Best practice:** Manage connections through a **connection pool** (e.g., HikariCP) rather than opening/closing connections per query — this avoids resource exhaustion under load. Currently, `DatabaseSetup.getConnection()` opens a new connection each time, which works for development but should be replaced with pooling before production use.
  - **Best practice:** Keep database credentials out of source code. Store them in environment variables or a `.env` file that is listed in `.gitignore`. The bot token is already read from `System.getenv("MOKEPONS_API_KEY")` — database credentials should follow the same pattern.
  - **Best practice:** Define your schema with versioned migration scripts (even simple numbered `.sql` files) so the database structure is reproducible and changes are tracked.
- **SLF4J + Logback** — logging framework. SLF4J provides the logging API (`Logger`, `LoggerFactory`), and Logback is the implementation. Configuration lives in `src/main/resources/logback.xml`.
  - **Best practice:** Use parameterized logging (`logger.info("Processing {}", value)`) rather than string concatenation — this avoids the cost of building the string when the log level is disabled.
- **JUnit 5 (Jupiter)** — testing framework for unit tests. Tests live in `src/test/java/pokemonGame/` and are run by the Maven Surefire plugin via `mvn test`.
  - **Best practice:** Write tests for game-logic classes (the domain layer). Since these classes have no I/O dependencies, they are straightforward to test. Each test class should focus on one domain class (e.g., `AttackTest` tests `Attack`, `PokemonTest` tests `Pokemon`).

## Architecture Overview

Entry point: `pokemonGame.App.main()` (for setup/testing); `pokemonGame.bot.BotRunner.main()` launches the Discord bot.

**Core domain classes** (in `src/main/java/pokemonGame/`):
- `Pokemon` — base class with Gen-III-style stat formulas (IVs, EVs, natures). Constructors are `protected`; species subclasses live in `pokemonGame.mons.*`. Also contains the static factory method `createPokemon(species, name, trainer)` that maps a species name string to the correct subclass constructor.
- `Move` — `abstract` base; each move is a concrete subclass in `pokemonGame.moves.*` with hardcoded stats passed to `super(name, power, type, category, accuracy, pp)`.
- `MoveSlot` — wraps a `Move` with mutable PP tracking. Pokémon movesets are `ArrayList<MoveSlot>` (max 4).
- `Attack` — stateless damage calculator. Uses `TypeChart` for effectiveness and implements STAB, crits, and a random factor.
- `Battle` — manages battle flow: `dealDamage()` applies calculated damage to the defender's HP, `enterBattleState()` determines turn order via speed, and `checkFainted()` flags knocked-out Pokémon.
- `TypeChart` — 18×18 float matrix indexed by string type names via a static `Map<String, Integer>`.
- `Natures` — enum of 25 natures; each stores its boosted/decreased `Stat` and exposes `modifierFor(Stat)`.
- `Stat` — simple enum: `HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED`.
- `LearnsetEntry` — associates a `Move` with a learning source (`LEVEL`, `TM`, etc.) and parameter (level number). `getEligibleMoves()` returns moves a Pokémon can learn at its current level.
- `Trainer` — holds a name, Discord ID, database ID, and `ArrayList<Pokemon>` team (max 6).

**Bot layer** (in `src/main/java/pokemonGame/bot/`):
- `BotRunner` — entry point for the Discord bot. Reads the bot token from the `MOKEPONS_API_KEY` environment variable, builds the JDA instance, registers event listeners, and declaratively registers all slash commands with Discord.
- `SlashExample` — extends JDA's `ListenerAdapter`, overrides `onSlashCommandInteraction()` to handle slash commands: `say`, `ping`, `battlestate`, `createtrainer`, `checkteam`, `addpokemon`, `releasepokemon`, `cleardatabase`. Each case delegates to domain/persistence methods and replies via JDA.
- `AutoCompleteBot` — extends `ListenerAdapter`, handles `onCommandAutoCompleteInteraction()` to provide autocomplete suggestions for slash command options.

**Persistence layer** (in `src/main/java/pokemonGame/db/`):
- `DatabaseSetup` — provides `getConnection()` for JDBC connections to MariaDB. Also contains `deleteAllData()` for development resets.
- `TrainerCRUD` — CRUD operations for the `trainers` table (create, read by Discord ID, update name, delete).
- `PokemonCRUD` — CRUD operations for the `pokemon_instances` table (create, read specific Pokémon for a trainer, update stats/HP, delete).
- `TeamCRUD` — manages the `trainer_teams` join table (add/remove Pokémon from team slots, retrieve full team, check slot availability).

**Layered design (separation of concerns):**

The codebase follows a layered pattern to keep responsibilities clear. This is a core OOP best practice — each layer has one job:

1. **Domain / Model layer** (`Pokemon`, `Move`, `Attack`, `Battle`, `TypeChart`, etc.) — pure game logic. No I/O, no Discord references, no database calls. This layer should be fully testable in isolation.
2. **Persistence / Data layer** (`pokemonGame.db.*`) — responsible for reading from and writing to MariaDB via JDBC. Methods here accept and return domain objects. They should never import JDA classes.
3. **Controller / Bot layer** (`pokemonGame.bot.*`) — JDA event listeners and slash command handlers. This layer translates Discord interactions into calls to the domain and persistence layers, then formats results as Discord embeds or messages.

**Why this matters:** Keeping layers separate means the game logic can be reused if the project ever moves to a different frontend (web, CLI, another chat platform). It also makes bugs easier to isolate — if damage calculation is wrong, you know to look in `Attack`, not in a Discord event handler.

## Adding a New Pokémon Species

All 151 Gen 1 Pokémon are already implemented. If a species needs to be re-created or the pattern understood:

1. Create `src/main/java/pokemonGame/mons/NewSpecies.java` extending `Pokemon`.
2. Declare a `private static final List<LearnsetEntry> LEARNSET` populated in a `static {}` block.
3. Constructor signature: `public NewSpecies(String name)` calling `super(species, dexIndex, type1, type2, level, hp, atk, def, spAtk, spDef, spd)`. **Default level is 5** for all species; use `setLevel()` after construction if a different level is needed.
4. In the constructor body: `setName(name)`, `setEvYield(...)`, `generateRandomIVs()`, `calculateCurrentStats()` — **in that order**.
5. Override `getLearnset()` to return `LEARNSET`.
6. Register the species in `Pokemon.createPokemon()` static factory switch statement.

Only **Gen 1 Pokémon** (Kanto dex #1–151) should be added. Example pattern — see `Bulbasaur.java`.

## Adding a New Move

Create `src/main/java/pokemonGame/moves/NewMove.java` extending `Move`:
```java
public class NewMove extends Move {
    public NewMove() {
        super("Move Name", power, "Type", "Physical|Special|Status", accuracy, pp);
    }
}
```
Move categories must be exactly `"Physical"`, `"Special"`, or `"Status"` — these strings drive which attack/defense stats are used in `Pokemon.getAttackStatForMove()`. Only Gen 1 moves should be added.

## Input / Interactivity Pattern

Since this project runs as a **Discord bot via JDA**, all user interaction flows through Discord events (slash commands, button clicks, select menus). The guiding principle is **separation of I/O from logic**:

- **Game-logic methods** (damage calculation, stat changes, move learning eligibility) should be pure — accept parameters, return results, never touch JDA, `Scanner`, or `System.console()` directly.
- **I/O lives in the bot/controller layer.** JDA event listeners collect user input from Discord, pass it into game-logic methods, and send results back as Discord messages or embeds.
- **Best practice:** Use JDA's `SlashCommandInteractionEvent`, `ButtonInteractionEvent`, etc. to gather user choices. Acknowledge interactions promptly (within 3 seconds) using `deferReply()` if processing takes time — Discord will show an error to the user otherwise.
- **Best practice:** For multi-step interactions (e.g., choosing which move to forget), use JDA's **button** or **select menu** components rather than waiting for freeform text messages. This makes the UX cleaner and avoids parsing ambiguity.
- `Pokemon.addMove()` and `LearnsetEntry.teachFromLearnset()` currently violate this by reading input internally — these should be refactored so the UI choice is made externally (via Discord components) and the result is passed in.

## Database (MariaDB) Conventions

- All persistent game state (trainer data, caught Pokémon, inventories, progress flags) is stored in **MariaDB**.
- Use **JDBC** with prepared statements for all queries. Example:
  ```java
  try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM trainers WHERE discord_id = ?")) {
      ps.setString(1, discordUserId);
      ResultSet rs = ps.executeQuery();
      // ...
  }
  ```
- **Best practice:** Wrap database access in dedicated **DAO (Data Access Object)** classes. A DAO class handles all SQL for one domain entity (e.g., `TrainerDAO`, `PokemonDAO`). This keeps SQL out of game logic and event handlers, and makes it straightforward to swap the database implementation later.
- **Best practice:** Use **try-with-resources** for `Connection`, `PreparedStatement`, and `ResultSet` objects so they are always closed properly — even if an exception occurs. Leaked connections are one of the most common bugs in JDBC applications.
- **Best practice:** Store database connection parameters (host, port, database name, username, password) in environment variables or a configuration file excluded from version control. Never hard-code credentials.
- Schema migrations should be tracked as numbered SQL files (e.g., `sql/001_create_trainers.sql`, `sql/002_create_pokemon.sql`) so any developer can recreate the database from scratch.

## Key Conventions

- **Types are strings**, not enums. Use exact capitalized names matching `TypeChart.TYPE_INDICES` keys: `"Normal"`, `"Fire"`, `"Water"`, `"Electric"`, `"Grass"`, `"Ice"`, `"Fighting"`, `"Poison"`, `"Ground"`, `"Flying"`, `"Psychic"`, `"Bug"`, `"Rock"`, `"Ghost"`, `"Dragon"`, `"Dark"`, `"Steel"`, `"Fairy"`.
- Secondary type is `null` (not `"None"`) for single-type Pokémon. `Attack.calculateEffectiveness` treats `null` as neutral.
- **Default level is 5** for all newly constructed Pokémon. `setLevel()` exists for creating Pokémon at other levels after construction.
- Stat calculation follows the mainline formula: `((2*base + IV + EV/4) * level / 100 + 5) * natureModifier`. HP uses a different formula (see `Pokemon.calcMaxHP`).
- EV setters are **additive** (they add to the current value) and enforce per-stat max 252 / total max 510.
- `Pokemon` constructors call `Natures.assignRandom(this)` automatically — do not assign natures manually in species constructors.
- **Best practice:** Follow Java naming conventions — `PascalCase` for classes, `camelCase` for methods and variables, `UPPER_SNAKE_CASE` for constants. This isn't just style; it signals intent (e.g., `LEARNSET` being a constant vs. `learnset` being a local variable).
- **Best practice:** Prefer `private` fields with getters/setters over `public` fields. Encapsulation lets you add validation or change internal representation later without breaking callers. The `Pokemon` class already follows this pattern.

## Build & Run

The project uses **Maven** as its build tool. All dependencies are declared in `pom.xml` and managed automatically — there is no `lib/` directory with manually downloaded JARs.

Common Maven commands:
```bash
mvn compile          # Compile main sources to target/classes/
mvn test             # Run JUnit 5 tests via Surefire plugin
mvn package          # Build a JAR (output in target/)
mvn clean            # Delete the target/ directory
```

Run the bot (after compiling):
```bash
mvn exec:java -Dexec.mainClass="pokemonGame.bot.BotRunner"
```
Or run the `App` class for local setup/testing:
```bash
mvn exec:java -Dexec.mainClass="pokemonGame.App"
```

The `MOKEPONS_API_KEY` environment variable must be set with the Discord bot token before running `BotRunner`.

**Best practice:** Always declare dependencies in `pom.xml` with explicit version numbers so builds are reproducible. Use `<scope>test</scope>` for test-only dependencies. Never drop JARs into the project manually — use Maven's dependency management instead.

## File Organization

The project follows the standard **Maven directory layout**:

```
src/
  main/
    java/pokemonGame/          # Core domain classes
    java/pokemonGame/bot/      # JDA event listeners and bot setup
    java/pokemonGame/db/       # Database/DAO classes (JDBC + MariaDB)
    java/pokemonGame/mons/     # Pokémon species subclasses (all 151 Gen 1)
    java/pokemonGame/moves/    # Move subclasses (Gen 1 moves)
    resources/                 # logback.xml and other config
  test/
    java/pokemonGame/          # JUnit 5 test classes
```

New files should be added in the appropriate package subdirectory:
- New Pokémon species: `src/main/java/pokemonGame/mons/`
- New moves: `src/main/java/pokemonGame/moves/`
- Core game-logic classes: `src/main/java/pokemonGame/`
- Database/DAO classes: `src/main/java/pokemonGame/db/`
- JDA event listeners and bot setup: `src/main/java/pokemonGame/bot/`
- Tests: `src/test/java/pokemonGame/`

When editing existing files, maintain the existing package structure and naming conventions. When adding new Pokémon or moves, ensure that they are registered in the appropriate places (e.g., new species in `Pokemon.createPokemon()`) to be accessible in the game. Follow the established patterns for constructors and method implementations to maintain consistency across the codebase.

## Work-in-Progress Areas

- Battle turn loop (`Battle.startTurn()`) is still a placeholder — `dealDamage()` applies damage to HP and `checkFainted()` works, but a full turn-based battle loop is not yet written.
- Input handling still has remnants of `Scanner` and `System.console()` in `App.java` — these need to be migrated to JDA interactions as the Discord bot integration matures.
- `Pokemon.addMove()` and `LearnsetEntry.teachFromLearnset()` currently violate this by reading input internally — these should be refactored so the UI choice is made externally (via Discord components) and the result is passed in.
- **Status effects** (burn, paralysis, poison, sleep, freeze) are planned for much later. Items and abilities are not in scope.
- Database schema and DAO layer may still be evolving — expect tables and queries to change as features are added.
- JDA bot setup (token management, command registration, event listener wiring) may need further organization as the command set grows.