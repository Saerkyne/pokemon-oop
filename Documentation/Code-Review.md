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



## 🟡 Important Issues

These should be addressed to improve reliability, maintainability, and correctness.


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


---

## 🟢 Nit / Style Issues

These are non-blocking suggestions for cleaner, more idiomatic Java.



### 18. `getMoveset()` Exposes Internal Mutable List

**File:** `src/main/java/pokemonGame/Pokemon.java`

```java
public ArrayList<MoveSlot> getMoveset() {
    return moveset;
}
``` m

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
