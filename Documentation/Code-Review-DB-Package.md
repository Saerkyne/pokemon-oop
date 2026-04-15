# Code Review — `pokemonGame.db` Package

**Date:** 2026-04-09  
**Files reviewed:** `DatabaseSetup.java`, `TrainerCRUD.java`, `PokemonCRUD.java`, `TeamCRUD.java`, `BattleCRUD.java`, `BattleTurnCRUD.java`, `MoveCRUD.java`

---

## Summary

The persistence layer is 7 files, ~900+ lines total. It uses HikariCP for connection pooling, prepared statements throughout (good security practice), and `try-with-resources` for all JDBC objects (good resource management). The `ALLOWED_TABLES` whitelist in `DatabaseSetup.deleteAllData()` is a well-implemented defense against SQL injection in dynamic table names. Issues range from a behavioral bug in pending action submission to excessive logging and tight coupling between CRUD classes.

---

## Issues

### DB-1 · BUG · `submitPendingAction()` doesn't use `ON DUPLICATE KEY UPDATE`

**File:** `BattleTurnCRUD.java`, lines 66–108

The detailed comment block at the top of the class explains the rationale for using `ON DUPLICATE KEY UPDATE` — it lets a player change their mind before the opponent submits. The comment even provides the complete refactored SQL. But the actual implementation uses a plain `INSERT`:

```java
String sql = "INSERT INTO battle_pending_actions (battle_id, trainer_id, "
    + "action_type, move_slot_index, switch_pokemon_id, submitted_at) "
    + "VALUES (?, ?, ?, ?, ?, ?)";
```

If a player submits an action and then changes their mind (e.g., clicks a different move button), the second `INSERT` throws a duplicate key error instead of updating the existing row. The error is caught and logged, but the player's revised choice is silently lost.

**Fix:** Use the SQL from the comment:
```java
String sql = "INSERT INTO battle_pending_actions (...) VALUES (?, ?, ?, ?, ?, ?) "
    + "ON DUPLICATE KEY UPDATE action_type = VALUES(action_type), "
    + "move_slot_index = VALUES(move_slot_index), "
    + "switch_pokemon_id = VALUES(switch_pokemon_id), "
    + "submitted_at = VALUES(submitted_at)";
```

---

### DB-2 · BUG · `BattleCRUD.setActivePokemon()` SQL concatenation + extra DB call

**File:** `BattleCRUD.java`, lines 173–182

```java
String sql = "UPDATE battles SET "
    + (trainerId == getBattleById(battleId).getTrainer1Id()
        ? "trainer1_active_pokemon_id"
        : "trainer2_active_pokemon_id")
    + "= ?, updated_at = ? WHERE battle_id = ?";
```

Two problems:

1. **Extra DB round-trip:** `getBattleById(battleId)` executes a full `SELECT * FROM battles` just to determine which column to update. This is called on every active Pokémon change.

2. **NPE risk:** If `getBattleById()` returns `null` (battle not found), `.getTrainer1Id()` throws `NullPointerException`.

3. **Missing space:** The concatenation produces `"trainer1_active_pokemon_id= ?"` (no space before `=`). MariaDB may accept this, but it's fragile.

**Why this matters (educational context):** While this isn't SQL injection (the column name is determined by a comparison, not user input), dynamically building column names in SQL strings is a pattern to avoid. It makes the code harder to read and creates non-obvious runtime failures.

**Fix — use two separate prepared statements or pass a column flag:**
```java
public void setActivePokemon(int battleId, boolean isTrainer1, int pokemonId) {
    String column = isTrainer1 ? "trainer1_active_pokemon_id" : "trainer2_active_pokemon_id";
    String sql = "UPDATE battles SET " + column + " = ?, updated_at = ? WHERE battle_id = ?";
    // ... (column is hardcoded, not user input, so this is safe)
}
```

The caller already knows whether they're trainer 1 or 2 from the Battle object they hold.

---

### DB-3 · ROBUSTNESS · `getConnection()` logs at INFO on every call

**File:** `DatabaseSetup.java`, line 85

```java
logger.info("Attempting to get database connection with URL: {}", URL);
```

Every single database operation starts by calling `getConnection()`, which logs at INFO level. With concurrent Discord users, this floods the log file. The URL never changes — it was already validated during static init.

**Fix:** Change to `TRACE` or `DEBUG`:
```java
logger.trace("Attempting to get database connection with URL: {}", URL);
```

---

### DB-4 · NIT · `addPokemonToDBTeam()` has `e.printStackTrace()` alongside logger

**File:** `TeamCRUD.java`, catch block around line 72

```java
} catch (SQLException e) {
    LOGGER.error("Error adding Pokemon to team: {}", e.getMessage(), e);
    e.printStackTrace();
    return -1;
}
```

`LOGGER.error("...", e.getMessage(), e)` already prints the full stack trace via Logback. The `e.printStackTrace()` call writes to `System.err`, bypassing the logging framework (no timestamp, no log level, no file rotation). This is the only CRUD method with this pattern — all others use only the logger.

**Fix:** Remove `e.printStackTrace();`.

---

### DB-5 · DESIGN · CRUD classes create other CRUD instances internally

**Files:**
- `TeamCRUD.removePokemonFromDBTeam()` — creates `new PokemonCRUD()` (line 100)
- `TeamCRUD.getDBTeamForTrainer()` — creates `new TrainerCRUD()` (line 130)
- `TeamCRUD.getPokemonInSlotForTrainer()` — creates `new TrainerCRUD()` (line 155)
- `PokemonCRUD.mapResultSetToPokemon()` — creates `new MoveCRUD()` (line 226)

Each internal instantiation is tight coupling. A `TeamCRUD` operation can't be tested without a real `TrainerCRUD` and `PokemonCRUD`. The DAO layer should be flat — each CRUD class handles its own table, and the **service layer** coordinates cross-table operations.

**Why this matters:** `removePokemonFromDBTeam()` deletes from `trainer_teams` AND from `pokemon_instances` (via `pokemonCRUD.deleteDBPokemon()`). This is a multi-table operation that belongs in `TeamService`, not in `TeamCRUD`. If you later need to change the deletion order or add cascading logic, you'd have to modify the DAO instead of the service.

**Fix:** Move cross-table orchestration to the service layer. The CRUD method should only delete from `trainer_teams`; the caller (service) handles deleting the Pokémon instance.

---

### DB-6 · ROBUSTNESS · `BattleCRUD.getActiveBattleForTrainerMatchup()` nested duplicate catch

**File:** `BattleCRUD.java`, lines 124–147

```java
try (Connection conn = DatabaseSetup.getConnection()) {
    // ...
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        // ...
    } catch (SQLException e) {
        LOGGER.error("Error retrieving active battle for trainer matchup: {} vs {}", trainer1Id, trainer2Id, e);
    }
} catch (SQLException e) {
    LOGGER.error("Error retrieving active battle for trainer matchup: {} vs {}", trainer1Id, trainer2Id, e);
}
```

The inner `catch` handles SQL errors from statement/result operations. The outer `catch` handles connection errors. Both log the same message. No other method in the CRUD layer uses this double-catch pattern.

**Fix:** Remove the inner catch — let the outer try-with-resources handle all SQL exceptions:
```java
try (Connection conn = DatabaseSetup.getConnection();
     PreparedStatement pstmt = conn.prepareStatement(sql)) {
    // ...
} catch (SQLException e) {
    LOGGER.error("Error retrieving active battle for trainer matchup: {} vs {}", trainer1Id, trainer2Id, e);
}
```

---

### DB-7 · ROBUSTNESS · Stub methods return empty arrays

**Files:**
- `BattleCRUD.getBattleHistoryForTrainer()` — queries DB but doesn't collect results, returns `new Battle[0]`
- `BattleCRUD.getAllActiveBattles()` — same pattern
- `BattleTurnCRUD.getTurnHistory()` — same pattern

These methods execute the SQL query and iterate the `ResultSet`, but the loop body is empty. The results are discarded and an empty array is returned.

**Not an immediate fix** — documented as work-in-progress. Included for tracking.

---

### DB-8 · NIT · `TrainerCRUD` redundant name assignment

**File:** `TrainerCRUD.java`, lines 49–52

```java
Trainer trainer = new Trainer(rs.getString("name"));
trainer.setTrainerDbId(rs.getInt("trainer_id"));
trainer.setTrainerName(rs.getString("name")); // redundant
trainer.setDiscordId(rs.getLong("discord_id"));
```

The `Trainer` constructor already sets `trainerName` from the parameter. The `setTrainerName()` call sets it to the same value again. This appears in both `getTrainerByDiscordId()` and `getTrainerByDbId()`.

**Fix:** Remove the `setTrainerName()` line.

---

### DB-9 · NIT · `addPokemonToDBTeam()` fallback team name masks bugs

**File:** `TeamCRUD.java`, lines 45–47

```java
String teamName = getTeamName(trainerId, teamId);
if (teamName == null) {
    teamName = "Default Team Name";
}
```

If `getTeamName()` returns null, the team doesn't exist for that trainer/team combination. Silently substituting "Default Team Name" hides the root cause. The insert may succeed with a wrong team name in the database.

**Fix:** Fail explicitly:
```java
String teamName = getTeamName(trainerId, teamId);
if (teamName == null) {
    LOGGER.error("No team found for trainer ID {} and team ID {}. Cannot add Pokémon.", trainerId, teamId);
    return -1;
}
```

---

### DB-10 · DESIGN · `PokemonCRUD` holds static `EvManager` instance for no reason

**File:** `PokemonCRUD.java`, line 36

```java
private static EvManager evManager = new EvManager();
```

`EvManager` has no instance state — all its data lives on the `Pokemon` object. This field exists solely because `setEv()` is an instance method (see COR-2 in the Core review). Once `EvManager` methods are made static, this field can be removed.

---

## Stats

| Severity     | Count |
|:-------------|:-----:|
| BUG          |   2   |
| DESIGN       |   3   |
| ROBUSTNESS   |   3   |
| NIT          |   2   |
| **Total**    | **10** |
