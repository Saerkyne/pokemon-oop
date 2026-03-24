# Pokemon-OOP — Code Review

**Review Date:** March 24, 2026
**Reviewer:** GitHub Copilot (automated review via Code Review Excellence skill)
**Scope:** Full codebase — domain layer, persistence layer, bot layer, tests, and build configuration

---

## Executive Summary

This is a well-structured learning project that demonstrates solid understanding of OOP fundamentals: inheritance hierarchies, encapsulation via private fields with getters/setters, polymorphism through abstract classes and overrides, and layered architecture separating domain logic from I/O. The test suite is thoughtful and well-commented with improvement suggestions built directly into the test documentation.

Key strengths:
- Clean separation of concerns across domain, persistence, and bot layers
- Correct implementation of Pokémon stat formulas (IVs, EVs, natures)
- Comprehensive type chart covering all 18 types
- Good use of prepared statements for database queries
- Thorough, well-documented test suite

Key areas for improvement:
- **🔴 Critical: Hardcoded database credentials in source code**
- **🔴 Critical: SQL injection vulnerability in `deleteAllData()`**
- Several design issues around error handling patterns
- Missing input validation at system boundaries
- Logging inconsistencies (mixing `System.out`, `java.util.logging`, and SLF4J)

---

## 🔴 Blocking Issues

These must be addressed before any production or shared deployment.

### 1. Hardcoded Database Credentials (Security — OWASP: Cryptographic Failures) WILL BE FIXED

**File:** `src/main/java/pokemonGame/db/DatabaseSetup.java` (lines 25-29)

```java
private static final String URL = "jdbc:mariadb://192.168.1.212:3306/pokemon_db";
private static final String USER = "pokemon_db_user";
private static final String PASSWORD = "fdr3invoices3MUY3wyatt";
```

The database password is committed to version control in plaintext. Even on a learning project, this is a critical security habit to fix. Anyone with access to the repository can see these credentials.

**Fix:** Read credentials from environment variables, matching the pattern already used for the bot token (`MOKEPONS_API_KEY`):

```java
private static final String URL = System.getenv("POKEMON_DB_URL");
private static final String USER = System.getenv("POKEMON_DB_USER");
private static final String PASSWORD = System.getenv("POKEMON_DB_PASSWORD");
```

Also add a `.env.example` file documenting the required variables, and ensure `.env` is in `.gitignore`. Consider rotating the exposed password since it's already in git history.

### 2. SQL Injection in `deleteAllData()` (Security — OWASP: Injection) HAS BEEN FIXED

**File:** `src/main/java/pokemonGame/db/DatabaseSetup.java` (lines 40-50)

```java
String tableName = rs.getString(1);
try (var delete = conn.prepareStatement("TRUNCATE TABLE " + tableName)) {
```

Table names retrieved from the database are concatenated directly into SQL. While this specific case uses names from `SHOW TABLES` (so the data comes from your own database, not from user input), the pattern itself is dangerous and teaches bad habits. If the database were ever compromised or if this pattern were copied to user-facing code, it would be exploitable.

**Fix:** Validate table names against a whitelist, or use database metadata APIs. For a dev-only reset function, hardcode the known table names:

```java
String[] tables = {"trainer_teams", "pokemon_instances", "trainers"};
for (String table : tables) {
    try (var stmt = conn.prepareStatement("TRUNCATE TABLE " + table)) {
        stmt.execute();
    }
}
```

### 3. `cleardatabase` Command Lacks Proper Authorization (Security — OWASP: Broken Access Control) HAS BEEN FIXED

**File:** `src/main/java/pokemonGame/bot/SlashExample.java` (lines 128-138)

The `cleardatabase` slash command only requires typing "CONFIRM" — any user who can see the command can wipe the entire database. While `setDefaultPermissions(DefaultMemberPermissions.DISABLED)` is set in `BotRunner`, this only controls visibility, not enforcement. The handler itself has no server-side permission check.

**Fix:** Add an explicit permission check in the handler:

```java
if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
    event.reply("You don't have permission to do this.").setEphemeral(true).queue();
    break;
}
```

---

## 🟡 Important Issues

These should be addressed to improve reliability, maintainability, and correctness.

### 4. Anti-Pattern: Using Exceptions for Control Flow in `Battle.dealDamage()` HAS BEEN FIXED

**File:** `src/main/java/pokemonGame/Battle.java` (lines 17-25)

```java
try {
    if ((defender.getCurrentHP() - damage) < 0) {
        throw new IllegalArgumentException("Damage cannot send HP negative...");
    }
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
    damage = defender.getCurrentHP();
}
```

This throws an exception and immediately catches it in the same method. Exceptions should signal unexpected conditions, not handle expected game logic. HP reaching zero is a normal battle outcome.

**Fix:** Use a simple conditional:

```java
if (damage > defender.getCurrentHP()) {
    damage = defender.getCurrentHP();
}
```

### 5. Inconsistent Logging (Three Different Logging Systems) WILL BE FIXED

> **Full inventory of every location that needs to be migrated to SLF4J:**
>
> **`java.util.logging` → SLF4J (SlashExample.java) — 8 occurrences:**
> - [ ] Line 13: `import java.util.logging.Logger;` — replace with SLF4J import
> - [ ] Line 34: `LOGGER.log(Level.INFO, "Received slash command: '" + event.getName() + "' with content: '" + ...` (say)
> - [ ] Line 39: `LOGGER.log(Level.INFO, "Received slash command: '" + event.getName() + "' ..."` (ping)
> - [ ] Line 44: `LOGGER.log(Level.INFO, ...)` (battlestate)
> - [ ] Line 49: `LOGGER.log(Level.INFO, ...)` (createtrainer)
> - [ ] Line 61: `LOGGER.log(Level.INFO, ...)` (checkteam)
> - [ ] Line 92: `LOGGER.log(Level.INFO, "... with Pokemon name: '" + ...` (addpokemon)
> - [ ] Line 138: `LOGGER.log(Level.INFO, "... with slot index: '" + ...` (releasepokemon)
> - [ ] Line 159: `LOGGER.log(Level.INFO, "... with confirmation: '" + ...` (cleardatabase)
>
> **`System.out/err` in domain layer (should be SLF4J DEBUG or removed):**
>
> *Attack.java — 8 active + 3 commented-out:*
> - [ ] Line 63: `System.out.println("STAB applied!");`
> - [ ] Line 65: `System.out.println("No STAB.");`
> - [ ] Line 68: `System.out.println("It" + (combinedEffectiveness == 0.0 ? ...` (effectiveness message)
> - [ ] Line 76: `System.out.println("Attacker level: " + level);`
> - [ ] Line 77: `System.out.println("Move power: " + power);`
> - [ ] Line 78: `System.out.println("Attacker attack stat: " + attackStat);`
> - [ ] Line 79: `System.out.println("Defender defense stat: " + defenseStat);`
> - [ ] Line 115: `System.out.println("Damage range: " + minDamage + " - " + maxDamage);`
> - [ ] Lines 54, 56, 59: commented-out `System.out.println` — delete dead code
>
> *Battle.java — 7 occurrences:*
> - [ ] Line 23: `System.out.println(attacker.getNickname() + " used " + move.getMoveName() + "!");`
> - [ ] Line 26: `System.out.println(defender.getNickname() + " took " + damage + " damage and has " + ...`
> - [ ] Line 28: `System.out.println(defender.getNickname() + " took " + damage + " damage and has fainted!");`
> - [ ] Line 43: `System.out.println("Battle has started between " + ...`
> - [ ] Line 44: `System.out.println(speedCheck.getName() + " will go first!");`
> - [ ] Line 50: `System.out.println(pokemon.getNickname() + " has fainted and cannot battle!");`
> - [ ] Line 52: `System.out.println(pokemon.getNickname() + " is ready to battle!");`
>
> *Pokemon.java — 2 occurrences:*
> - [ ] Line 615: `System.out.println("No species selected. Please choose a valid Pokémon species.");`
> - [ ] Line 776: `System.out.println("Species not recognized. Please choose a valid Pokémon species.");`
>
> *Trainer.java — 2 occurrences:*
> - [ ] Line 54: `System.out.println("Added " + pokemon.getNickname() + " (" + pokemon.getSpecies() + ") to " + ...`
> - [ ] Line 57: `System.out.println("Team is full! Cannot add more Pokémon.");`
>
> **`System.out/err` in persistence layer (should be SLF4J):**
>
> *TrainerCRUD.java — 8 occurrences:*
> - [ ] Line 16: `System.out.println("Trainer '" + name + "' created successfully.");`
> - [ ] Line 21: `System.out.println("New Trainer ID: " + trainerId);`
> - [ ] Line 27: `System.err.println("Error creating trainer: " + e.getMessage());`
> - [ ] Line 48: `System.out.println("Trainer '" + trainer.getName() + "' retrieved successfully.");`
> - [ ] Line 51: `System.out.println("No trainer found with Discord ID: " + discordID);`
> - [ ] Line 57: `System.err.println("Error retrieving trainer: " + e.getMessage());`
> - [ ] Line 71/74: `System.out.println` in deleteTrainerByDiscordId (success/not-found)
> - [ ] Line 79: `System.err.println("Error deleting trainer: " + e.getMessage());`
> - [ ] Line 94/97: `System.out.println` in updateTrainerNameByDiscordId (success/not-found)
> - [ ] Line 102: `System.err.println("Error updating trainer: " + e.getMessage());`
>
> *PokemonCRUD.java — 10 occurrences:*
> - [ ] Line 46: `System.out.println("Pokemon '" + ... + "' created successfully...");`
> - [ ] Line 52: `System.out.println("New Pokemon ID: " + pokemonId);`
> - [ ] Line 58: `System.err.println("Error creating Pokemon: " + e.getMessage());`
> - [ ] Line 75: `System.out.println("Pokemon '" + ... + "' retrieved successfully...");`
> - [ ] Line 79: `System.out.println("No Pokemon found with ID: " + ...);`
> - [ ] Line 85: `System.err.println("Error retrieving Pokemon: " + e.getMessage());`
> - [ ] Line 125: `System.out.println("Pokemon '" + ... + "' updated successfully...");`
> - [ ] Line 129: `System.out.println("No Pokemon found with ID: " + ...);`
> - [ ] Line 134: `System.err.println("Error updating Pokemon: " + e.getMessage());`
> - [ ] Lines 149/153/158: delete/not-found/error messages
>
> *TeamCRUD.java — 13 occurrences:*
> - [ ] Line 14: `System.err.println("Error checking slot index for trainer ID " + ...);`
> - [ ] Line 17: `System.out.println("Trainer ID " + ... + "'s team is full!...");`
> - [ ] Line 26: `System.out.println("Attempting to add Pokemon with ID " + ...);`
> - [ ] Line 30: `System.out.println("Pokemon with ID " + ... + " added...");`
> - [ ] Line 35: `System.err.println("Failed to insert team entry...");`
> - [ ] Line 40: `System.err.println("Error adding Pokemon to team: " + ...);`
> - [ ] Line 61: `System.err.println("Error checking slot index: " + ...);`
> - [ ] Line 84: `System.out.println("Pokemon in slot " + ... + " removed...");`
> - [ ] Line 89: `System.out.println("No Pokemon in slot " + ...);`
> - [ ] Line 94: `System.err.println("Error removing Pokemon from team: " + ...);`
> - [ ] Line 118: `System.err.println("Error retrieving team...");`
> - [ ] Line 141: `System.err.println("Error retrieving Pokemon in slot...");`
> - [ ] Line 172: `System.err.println("Error reordering team after release...");`
>
> *App.java — ~40 occurrences:* Many are inside commented-out blocks. The active ones in `createTrainer()` and `teachMoveFromLearnset()` should use SLF4J. The commented-out blocks should be deleted entirely.
>
> **Already correct (no action needed):**
> - `DatabaseSetup.java` — already uses SLF4J ✅

The codebase uses three different logging mechanisms:

| System | Where Used |
|--------|-----------|
| `System.out.println` / `System.err.println` | `Attack.java`, `Battle.java`, `Trainer.java`, all CRUD classes |
| `java.util.logging.Logger` | `SlashExample.java` |
| SLF4J + Logback | `DatabaseSetup.java` |

The project includes SLF4J + Logback as dependencies and has a `logback.xml` config. All logging should use SLF4J.

**Fix:** Replace `System.out.println` debug statements and `java.util.logging` with SLF4J:

```java
private static final Logger logger = LoggerFactory.getLogger(ClassName.class);
// Then:
logger.info("Processing {}", value);  // parameterized, not concatenated
```

Additionally, `SlashExample.java` uses string concatenation in log statements:
```java
LOGGER.log(Level.INFO, "Received slash command: '" + event.getName() + "' ...");
```
This builds the string even when the log level is disabled. SLF4J's parameterized syntax avoids this cost.

### 6. `System.out.println` Statements in Domain Logic WILL BE FIXED

**File:** `src/main/java/pokemonGame/Attack.java` (lines 57-62)

```java
System.out.println("STAB applied!");
System.out.println("It" + (combinedEffectiveness == 0.0 ? ...));
System.out.println("Attacker level: " + level);
System.out.println("Move power: " + power);
```

Domain-layer classes (`Attack`, `Battle`, `Trainer`) write directly to `System.out`. This violates the layered architecture: the domain layer should have no I/O. These debug statements will appear in the Discord bot's server logs mixed with actual log output, and they cannot be turned off without removing the code.

**Fix:** Either remove these `println` calls or replace them with SLF4J logger calls at `DEBUG` level so they can be controlled via `logback.xml`.

### 7. `TypeChart` — Missing Null/Invalid Input Handling HAS BEEN FIXED

**File:** `src/main/java/pokemonGame/TypeChart.java` (lines 41-46)

```java
public float getEffectiveness(String moveType, String pokemonType) {
    int moveIndex = TYPE_INDICES.get(moveType);
    int pokemonIndex = TYPE_INDICES.get(pokemonType);
    return typeChart[moveIndex][pokemonIndex];
}
```

If either type string is misspelled or null, `TYPE_INDICES.get()` returns `null`, and the unboxing to `int` throws a `NullPointerException`. Since type strings come from user-created data (move definitions, species definitions), a typo in any of the 151 species files or move files would cause a crash with no helpful error message.

**Fix:** Add validation with a clear error message:

```java
public float getEffectiveness(String moveType, String pokemonType) {
    Integer moveIndex = TYPE_INDICES.get(moveType);
    Integer pokemonIndex = TYPE_INDICES.get(pokemonType);
    if (moveIndex == null) throw new IllegalArgumentException("Unknown move type: " + moveType);
    if (pokemonIndex == null) throw new IllegalArgumentException("Unknown pokemon type: " + pokemonType);
    return typeChart[moveIndex][pokemonIndex];
}
```

### 8. `TypeChart` Instance Created for Every `Attack` Object — Should Be Shared HAS BEEN FIXED

**File:** `src/main/java/pokemonGame/Attack.java` (line 6)

```java
private static final TypeChart TYPE_CHART = new TypeChart();
```

While this is `static final` (good — only one instance per class), the `TypeChart` itself allocates a `new float[18][18]` array every time it's constructed. Since the data is immutable, `TypeChart` should use a `static final` array internally, or the whole class should be a utility class with static methods and a static initializer.

### 9. `Battle` Methods Are All Static — Not Object-Oriented

**File:** `src/main/java/pokemonGame/Battle.java`

Every method is `static`, and `Battle` has a `public static void main` that does nothing. This means `Battle` is used as a namespace for functions rather than as an object that models a battle's state. As the battle system grows (turn tracking, weather, field effects), this will become unsustainable.

**Fix:** Make `Battle` an instance class that encapsulates battle state:

```java
public class Battle {
    private final Trainer player;
    private final Trainer opponent;
    private int turnCount;
    // ...
    public Battle(Trainer player, Trainer opponent) { ... }
    public void dealDamage(Pokemon attacker, Pokemon defender, Move move) { ... }
}
```

### 10. `Pokemon.createPokemon()` — Giant Switch Statement (OCP Violation)

**File:** `src/main/java/pokemonGame/Pokemon.java` (lines ~630-810)

The factory method contains a 151-case `switch` statement. Adding any new species requires modifying this core class — a violation of the Open/Closed Principle.

💡 **[suggestion]** For a learning project, the switch statement is perfectly functional and easy to understand. But as an educational note: the scalable approach would be a `Map<String, Function<String, Pokemon>>` registry that species register themselves into, or using `ServiceLoader`. This way, adding a new species would only require creating the new class file — the factory wouldn't need modification.

### 11. `PokemonCRUD.updateDBPokemon()` — WHERE Clause Uses Discord ID Instead of Trainer DB ID HAS BEEN FIXED

**File:** `src/main/java/pokemonGame/db/PokemonCRUD.java` (lines 83-84)

```java
pstmt.setInt(20, pokemon.getId());
pstmt.setLong(21, pokemon.getTrainerDiscordId());
```

The SQL WHERE clause is `WHERE instance_id = ? AND trainer_id = ?`, but it binds `getTrainerDiscordId()` (a Discord snowflake) to the `trainer_id` column (a database integer). This will never match any rows, silently failing to update.

**Fix:** Use `pokemon.getTrainerDBId()` instead of `pokemon.getTrainerDiscordId()`.

### 12. `PokemonCRUD.mapResultSetToPokemon()` — EV Setters Are Additive, Causing Double-Counting

**File:** `src/main/java/pokemonGame/db/PokemonCRUD.java` (lines 140-155)

```java
foundPokemon.setEvHp(evHp);
foundPokemon.setEvAttack(evAttack);
// ...
```

The EV "setters" on `Pokemon` are actually **additive** — they add to the current value and track the total. Since `createPokemon()` initializes all EVs to 0, this works on the first call. But if `mapResultSetToPokemon()` were called twice on the same object, or if a species constructor ever set non-zero EVs, the EVs would be wrong. The naming convention `setEvHp` implies assignment, but the implementation is `this.evHp += actual`.

**Fix:** Either rename the methods to `addEvHp()` to reflect their behavior, or add true setters that assign the value directly. For database reconstruction, direct assignment setters are needed.

### 13. New Connection Per Query — No Connection Pooling

**File:** `src/main/java/pokemonGame/db/DatabaseSetup.java`

Every CRUD method calls `DatabaseSetup.getConnection()`, which opens a new JDBC connection via `DriverManager.getConnection()`. In `TeamCRUD.addPokemonToDBTeam()`, the method calls `checkSlotIndex()` (which opens its own connection), then opens another connection for the INSERT — that's 2 connections for one logical operation.

💡 **[suggestion]** For development this works, but consider switching to HikariCP for connection pooling before scaling. The change is minimal — replace `DriverManager.getConnection()` with `dataSource.getConnection()` from a `HikariDataSource`.

### 14. `Statement.RETURN_GENERATED_KEYS` Used Unnecessarily on SELECT/DELETE Statements

**Files:** `TrainerCRUD.java`, `PokemonCRUD.java`, `TeamCRUD.java`

Several `prepareStatement` calls pass `Statement.RETURN_GENERATED_KEYS` even for SELECT and DELETE queries where no keys are generated:

```java
// In getTrainerByDiscordId — this is a SELECT
try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
```

This is harmless but misleading. It suggests the code expects generated keys from a query that doesn't produce any.

**Fix:** Only use `Statement.RETURN_GENERATED_KEYS` on INSERT statements. For other queries, use the default `conn.prepareStatement(sql)`.

---

## 🟢 Nit / Style Issues

These are non-blocking suggestions for cleaner, more idiomatic Java.

### 15. Java Naming Convention Violations

| Location | Issue | Convention |
|----------|-------|-----------|
| `Pokemon.DexIndex` | Field starts with uppercase | Should be `dexIndex` |
| `Pokemon.MaxHP` | Field starts with uppercase | Should be `maxHP` |
| `Trainer.DBid` | Mixed case inconsistency | Should be `dbId` |
| `Pokemon.getIsFainted()` | Getter for `Boolean` should use `is` prefix | Should be `isFainted()` |
| EV setters (`setEvHp`) | Named like setters but behave as adders | Should be `addEvHp()` |

### 16. Use `boolean` Instead of `Boolean` for `isFainted`

**File:** `src/main/java/pokemonGame/Pokemon.java` (line 55)

```java
private Boolean isFainted = false;
```

Using the boxed `Boolean` type instead of the primitive `boolean` allows null values and adds unnecessary auto-boxing overhead. Since `isFainted` is always either `true` or `false`, use the primitive.

### 17. `Trainer.addPokemonToTeam()` — Return Value Instead of Print

**File:** `src/main/java/pokemonGame/Trainer.java` (lines 52-59)

```java
public void addPokemonToTeam(Pokemon pokemon) {
    if (team.size() < 6) {
        team.add(pokemon);
        System.out.println("Added " + pokemon.getNickname() + "...");
    } else {
        System.out.println("Team is full!...");
    }
}
```

This domain method prints directly to the console. It should return a boolean or throw so the caller (the bot layer) can decide how to communicate the result.

### 18. `getMoveset()` Exposes Internal Mutable List

**File:** `src/main/java/pokemonGame/Pokemon.java`

```java
public ArrayList<MoveSlot> getMoveset() {
    return moveset;
}
```

Returning the internal `ArrayList` directly allows external code to bypass the 4-move limit by calling `getMoveset().add(...)` directly. Consider returning `Collections.unmodifiableList(moveset)` or using `List<MoveSlot>` as the return type.

### 19. `Attack.calculateDamage()` — Status Moves Would Cause Division by Zero

**File:** `src/main/java/pokemonGame/Attack.java` (line 82)

```java
int defenseStat = defender.getDefenseStatForMove(move);
```

For Status moves, `getDefenseStatForMove()` returns 0, leading to division by zero on this line:

```java
int baseDamage = (levelCalc * power * attackStat) / defenseStat;
```

Status moves (like Growl, Toxic) shouldn't go through the damage calculator at all, but there's no guard against it.

**Fix:** Add an early return at the start of `calculateDamage()`:

```java
if ("Status".equals(move.getMoveCategory())) return 0;
```

### 20. `MoveSlot.use()` — Consider Returning a Boolean Instead of Throwing

**File:** `src/main/java/pokemonGame/MoveSlot.java` (lines 10-15)

```java
public void use() {
    if (currentPP > 0) {
        currentPP--;
    } else {
        throw new IllegalStateException("No PP left for this move!");
    }
}
```

Running out of PP is a normal game condition (not an exceptional state). Throwing an exception means every caller must wrap this in try-catch. A `boolean` return would be cleaner for the battle loop to check.

### 21. `AutoCompleteBot` Is Registered But Never Added as Event Listener

**File:** `src/main/java/pokemonGame/bot/BotRunner.java` (line 17)

```java
.addEventListeners(new SlashExample())
```

Only `SlashExample` is registered. `AutoCompleteBot` exists but is never added to the JDA builder, so autocomplete won't work for slash commands.

**Fix:** Register both listeners:

```java
.addEventListeners(new SlashExample(), new AutoCompleteBot())
```

### 22. `SlashExample` — Missing `break` After `addpokemon` Case

**File:** `src/main/java/pokemonGame/bot/SlashExample.java`

The `addpokemon` case block uses `break` inside nested if/else branches, but the case itself doesn't end with a `break` at the outer level. If any code path within the nested logic doesn't hit a `break`, execution falls through to `releasepokemon`. This is a fragile pattern — using `return` instead of `break` or restructuring to avoid fall-through risk would be safer.

### 23. `SlashExample.say()` — XSS-Adjacent: Bot Echoes User Content Verbatim

**File:** `src/main/java/pokemonGame/bot/SlashExample.java` (lines 143-145)

```java
public void say(SlashCommandInteractionEvent event, String content) {
    event.reply(content).queue();
}
```

The bot repeats whatever the user types. While Discord itself sanitizes message content, this could be used for social engineering (making it appear the bot said something it didn't). Consider prefixing the response or removing this command before production.

### 24. Slot Number Calculation Is Unnecessarily Complex

**File:** `src/main/java/pokemonGame/bot/SlashExample.java` (line 62)

```java
int slotNumber = teamInfo.size() - teamInfo.size() + 1;
```

This always evaluates to `1`. Simplify to:

```java
int slotNumber = 1;
```

### 25. `Bulbasaur` vs `Charizard` — Inconsistent `setCurrentHP` Call

**File:** `src/main/java/pokemonGame/mons/Bulbasaur.java` (line 66)

Bulbasaur calls `this.setCurrentHP(getMaxHP())` after `calculateCurrentStats()`, but Charizard does not. Since `calculateCurrentStats()` already sets `currentHP = MaxHP`, the extra call in Bulbasaur is redundant but harmless. The inconsistency could confuse readers.

**Fix:** Remove the extra `setCurrentHP` call from Bulbasaur (and any other species that have it), or add it consistently to all species.

---

## 💡 Suggestions / Educational Notes

### 26. Learnset `static {}` Blocks Create New Move Instances

**File:** All species files (e.g., `Bulbasaur.java`)

```java
LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.LEVEL, 1));
```

Each species creates new `Move` objects in its static initializer. Since `Move` subclasses are stateless (they have no mutable fields), they could be shared as singletons. For 151 species × ~20 moves each, that's thousands of identical immutable objects.

For a learning project this is fine, but the concept of the **Flyweight pattern** applies here: share identical immutable objects rather than creating new ones each time.

### 27. Consider Using `sealed` Classes (Java 21)

Since this project uses Java 21, the `Move` hierarchy is a candidate for `sealed` classes:

```java
public abstract sealed class Move permits Tackle, Psychic, Flamethrower, ... {
```

This tells the compiler exactly which subclasses exist, enabling exhaustive `switch` expressions and preventing unauthorized subclassing. However, with 100+ move classes, the permits list would be very long, so this is more of an educational note than a practical suggestion for this project.

### 28. `Pokemon.generateRandomIVs()` Creates a New `Random` Each Call

**File:** `src/main/java/pokemonGame/Pokemon.java`

```java
public void generateRandomIVs() {
    Random rand = new Random();
    // ...
}
```

Creating a new `Random` each time is wasteful and could reduce randomness quality if called in quick succession (same seed from system clock). Use a shared `static final Random` field, as `Attack` already does with its `RNG` field.

---

## 📋 Test Suite Assessment

### Strengths

- **Excellent documentation:** Each test includes `CHECKS`, `HOW`, and `IMPROVE` comments explaining intent — a great learning practice
- **Good coverage of core domain:** Tests exist for `Pokemon`, `Attack`, `Battle`, `Trainer`, `TypeChart`, `Natures`, and `LearnsetEntry`
- **Parameterized tests:** `AttackTest` uses `@ParameterizedTest` with `@MethodSource` for comprehensive type chart testing
- **Edge cases considered:** Tests for null Pokémon in learnset, speed ties in battle, team size limits

### Gaps

| Area | Coverage | Notes |
|------|----------|-------|
| Domain classes | ✅ Good | Core mechanics well-tested |
| `Move` / `MoveSlot` | ⚠️ Partial | `MoveSlot.use()` and PP management untested |
| `TypeChart` | ✅ Good | Both individual tests and parameterized tests |
| `createPokemon()` factory | ❌ Missing | No tests verifying the factory switch statement |
| Database CRUD | ❌ Missing | No integration tests (expected — requires DB) |
| Bot layer | ❌ Missing | No tests (expected — requires JDA mock) |
| Status moves path | ❌ Missing | Division-by-zero for Status category untested |
| EV overflow logic | ⚠️ Partial | Some tests exist but not all edge cases |

### Recommendations

1. Add a `MoveSlotTest` class testing `use()`, `restore()`, and the out-of-PP exception
2. Add a factory test that verifies `Pokemon.createPokemon("bulbasaur", "Test", trainer)` produces a `Bulbasaur` instance
3. Add a test that calls `calculateDamage()` with a Status move to document the division-by-zero issue
4. Consider mocking the database layer for integration-like tests

---

## 📋 Build Configuration Assessment

**File:** `pom.xml`

### Issues

1. **Missing `slf4j-api` version:** The direct dependency for `slf4j-api` has no `<version>` tag — it relies on `<dependencyManagement>` to provide version `2.0.7`. This works but is fragile; if the management section is removed, the build breaks with a confusing error.

2. **Version mismatch potential:** SLF4J 2.0.7 is managed in `dependencyManagement`, but Logback 1.5.6 is a direct dependency. These should be kept compatible — Logback 1.5.x requires SLF4J 2.0.x, so the current versions are fine, but version drift could cause `NoSuchMethodError` at runtime.

3. **No `maven-enforcer-plugin`:** Consider adding it to enforce minimum Maven/Java versions and prevent dependency convergence issues.

### Positive Notes

- Java 21 via `maven.compiler.release` — correct modern approach
- UTF-8 encoding explicitly set
- Test dependencies correctly scoped with `<scope>test</scope>`
- All dependency versions are explicit (except SLF4J, noted above)
- Surefire plugin version is current

---

## Architecture Summary

```
┌─────────────────────────────────────────┐
│     Bot / Controller Layer              │  SlashExample, AutoCompleteBot, BotRunner
│     (JDA event handlers)                │  
├─────────────────────────────────────────┤
│     Domain / Model Layer                │  Pokemon, Move, Attack, Battle, TypeChart,
│     (Pure game logic, no I/O)           │  Trainer, MoveSlot, Natures, Stat
├─────────────────────────────────────────┤
│     Persistence / Data Layer            │  TrainerCRUD, PokemonCRUD, TeamCRUD,
│     (JDBC + MariaDB)                    │  DatabaseSetup
└─────────────────────────────────────────┘
```

**Verdict:** The layered architecture is well-conceived and mostly followed. The main violations are `System.out.println` calls in domain classes and the `teachMoveFromLearnset()` console I/O in `App.java` (which is documented as a known WIP in copilot-instructions). The project demonstrates a clear understanding of why layers matter and how to separate concerns.

---

## Summary Table

| # | Severity | Category | Title |
|---|----------|----------|-------|
| 1 | 🔴 Blocking | Security | Hardcoded database credentials |
| 2 | 🔴 Blocking | Security | SQL injection in `deleteAllData()` |
| 3 | 🔴 Blocking | Security | `cleardatabase` lacks server-side auth check |
| 4 | 🟡 Important | Design | Exception used for control flow in `dealDamage()` |
| 5 | 🟡 Important | Consistency | Three different logging systems |
| 6 | 🟡 Important | Architecture | `System.out` in domain layer classes |
| 7 | 🟡 Important | Robustness | `TypeChart.getEffectiveness()` missing null check |
| 8 | 🟡 Important | Performance | `TypeChart` instance per `Attack` object |
| 9 | 🟡 Important | Design | `Battle` is all-static, not object-oriented |
| 10 | 🟡 Important | Design | 151-case switch in `createPokemon()` factory |
| 11 | 🟡 Important | Bug | `updateDBPokemon()` uses Discord ID where DB ID expected |
| 12 | 🟡 Important | Bug | EV setters are additive but named like assignments |
| 13 | 🟡 Important | Performance | New JDBC connection per query |
| 14 | 🟡 Important | Clarity | `RETURN_GENERATED_KEYS` on non-INSERT queries |
| 15 | 🟢 Nit | Style | Java naming convention violations |
| 16 | 🟢 Nit | Style | Boxed `Boolean` for `isFainted` |
| 17 | 🟢 Nit | Architecture | `Trainer.addPokemonToTeam()` prints to console |
| 18 | 🟢 Nit | Encapsulation | `getMoveset()` exposes internal mutable list |
| 19 | 🟢 Nit | Bug | Status moves cause division by zero |
| 20 | 🟢 Nit | Design | `MoveSlot.use()` throws for normal game condition |
| 21 | 🟢 Nit | Bug | `AutoCompleteBot` never registered as listener |
| 22 | 🟢 Nit | Bug | Missing `break` risk in `addpokemon` case |
| 23 | 🟢 Nit | Security | `say` command echoes user input verbatim |
| 24 | 🟢 Nit | Clarity | Unnecessarily complex slot number calculation |
| 25 | 🟢 Nit | Consistency | Inconsistent `setCurrentHP` in species constructors |
| 26 | 💡 Suggestion | Performance | Shared Move instances (Flyweight pattern) |
| 27 | 💡 Suggestion | Java 21 | Sealed classes for Move hierarchy |
| 28 | 💡 Suggestion | Performance | Shared `Random` instance in `generateRandomIVs()` |

---

## 🎉 Praise — What's Done Well

1. **Test documentation is outstanding.** The `CHECKS` / `HOW` / `IMPROVE` comment pattern on every test is a practice most professional codebases don't achieve. It makes the tests self-documenting and serves as a learning record.

2. **Prepared statements everywhere in CRUD code.** All user-facing database queries use parameterized prepared statements, preventing SQL injection in the critical data paths.

3. **Clean inheritance model.** The `Pokemon` → species subclass hierarchy with protected constructors and `static final LEARNSET` patterns is well-designed. Each species is self-contained and follows a consistent template.

4. **Nature system is elegant.** The `Natures` enum with `modifierFor(Stat)` is clean, type-safe, and avoids the need for lookup tables or string matching.

5. **EV cap enforcement is correct.** The EV setters correctly enforce both the per-stat 252 cap and the total 510 cap, matching the mainline games.

6. **Layered architecture is understood and mostly followed.** The separation between domain, persistence, and bot layers is real — game logic doesn't import JDA, and the intent to keep layers clean is clear.

7. **Good use of ephemeral replies.** Error messages in `SlashExample` use `.setEphemeral(true)`, keeping error noise out of public channels — this is a JDA best practice.
