# Multi-Team Migration Plan

Guide for updating the codebase so each trainer can have multiple named teams (up to 3 teams of 6 Pokémon each).

---

## Current State

### What's Done

- **TeamCRUD.java** — Already multi-team-ready. Every method accepts a `teamId` parameter, and the planned schema uses `team_id AUTO_INCREMENT PRIMARY KEY` with a `UNIQUE (trainer_id, team_name, slot_index)` constraint.
- **`/createteam` command** — Registered in BotRunner with a `teamname` option. Handler code exists in SlashExample (line 99), though it has a static-call bug.

### What's Broken Right Now (10 Compile Errors)

The bot layer still calls TeamCRUD methods with the **old single-team signatures** (missing `teamId`). The project does not compile. Every error is in one of two files:

| File | Errors |
|------|--------|
| `SlashExample.java` | 8 errors — missing `teamId` in calls to `getDBTeamForTrainer`, `checkSlotIndex`, `addPokemonToDBTeam`, `getSlotIndexForPokemon`, `removePokemonFromDBTeam`, `reorderTeamAfterRelease`; plus a static-call bug on `createTeamForTrainer` |
| `AutoCompleteBot.java` | 1 error — missing `teamId` in `getDBTeamForTrainer` call |

### Bug in TeamCRUD Itself

`checkSlotIndex()` (line 84) tries to read `rs.getString("team_name")` in a log message, but the query is `SELECT COUNT(*) AS slot_index` — there is no `team_name` column in the result set. This will throw a `SQLException` at runtime. Fix: either remove `team_name` from the log message or add a JOIN/subquery to include it.

---

## Database Schema Change

### Old Schema (single team, currently in the "Run This First" section of Database-Documentation.md)

```sql
CREATE TABLE trainer_teams (
    trainer_id   INT NOT NULL,
    slot_index   SMALLINT NOT NULL,
    instance_id  INT NOT NULL,
    PRIMARY KEY (trainer_id, slot_index),
    FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id),
    FOREIGN KEY (instance_id) REFERENCES pokemon_instances(instance_id),
    CHECK (slot_index BETWEEN 0 AND 5)
);
```

### New Schema (multiple named teams per trainer)

```sql
CREATE TABLE trainer_teams (
    team_id      INT AUTO_INCREMENT PRIMARY KEY,
    trainer_id   INT NOT NULL,
    team_name    VARCHAR(50) NOT NULL,
    slot_index   SMALLINT NOT NULL,
    instance_id  INT NOT NULL,
    UNIQUE (trainer_id, team_name, slot_index),
    FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id),
    FOREIGN KEY (instance_id) REFERENCES pokemon_instances(instance_id),
    CHECK (slot_index BETWEEN 0 AND 5)
);
```

### Migration Steps

1. Back up the existing `trainer_teams` data.
2. `DROP TABLE trainer_teams;`
3. Run the new `CREATE TABLE` statement above.
4. Re-insert backed-up rows with a default `team_name` (e.g., `'Main'`) and let `team_id` auto-generate.
5. Update the "Database Schema (Run This First)" section in `Database-Documentation.md` to match the new schema.

---

## File-by-File Change Plan

### Layer 1 — Database / Persistence

#### `TeamCRUD.java` — Bug fix only

| Item | What to Change |
|------|---------------|
| **`checkSlotIndex()` line 84** | Remove `rs.getString("team_name")` from the log message, or change the query to include `team_name` via a JOIN. The current `COUNT(*)` query has no `team_name` column in its result set. |

Everything else in this file is already multi-team-ready — no structural changes needed.

#### `DatabaseSetup.java` — No code changes

The `deleteAllData()` whitelist already includes `trainer_teams`. The schema itself lives in comments in TeamCRUD and in Database-Documentation.md, not in DatabaseSetup.

#### `BattleCRUD.java` — Add team references to battles

The `battles` table currently links two trainers but **does not record which team each trainer is using**. When a trainer can have multiple teams, the battle needs to know which one.

| Item | What to Change |
|------|---------------|
| **`createBattle()` SQL** | Add `trainer1_team_id INT` and `trainer2_team_id INT` columns to the `battles` INSERT. Accept `teamId` parameters. |
| **`getBattleById()` / `getActiveBattleForTrainer()`** | Read and populate the new `team_id` columns from the result set. |
| **`battles` table schema** | `ALTER TABLE battles ADD COLUMN trainer1_team_id INT, ADD COLUMN trainer2_team_id INT;` with foreign keys to `trainer_teams(team_id)`. |

#### `Database-Documentation.md` — Update schema section

Update the `trainer_teams` CREATE TABLE in the "Database Schema (Run This First)" block (around line 318) and in the "Database Table Designs" section (around line 158) to match the new multi-team schema. Add the `battles` table schema changes if applicable.

---

### Layer 2 — Domain Model

#### `Trainer.java` — Decide: single active team in memory, or multiple?

The `Trainer` class currently has one `List<Pokemon> team` field. Two options:

**Option A — Single loaded team (recommended for now):**
Keep `Trainer` as-is. When a battle starts, load the correct team from the database into the `team` field. The `Trainer` object in memory always represents "the team currently in use." This is simpler and avoids changing `Battle`, `TurnManager`, and every method that calls `trainer.getTeam()`.

**Option B — Multiple teams in memory:**
Change `team` to `Map<Integer, List<Pokemon>>` keyed by `teamId`. Every call to `getTeam()` would need a `teamId` parameter. This ripples into `Battle.java`, `TurnManager.java`, `BattleService.java`, and all tests.

**Recommendation:** Go with Option A. The database already stores multiple teams. When you need a team in Java (for a battle, for `/checkteam`), load the specific one via `teamCRUD.getDBTeamForTrainer(trainerId, teamId)` and set it on the `Trainer` object. This keeps the domain model simple.

#### `BattleService.java` — Accept `teamId` parameters

| Item | What to Change |
|------|---------------|
| **`createBattle()`** | Change signature to `createBattle(int trainer1Id, int trainer1TeamId, int trainer2Id)`. Pass `trainer1TeamId` to `BattleCRUD.createBattle()`. Trainer 2's team can be set later when they accept the challenge. |

#### `Battle.java` — Store team IDs

| Item | What to Change |
|------|---------------|
| Add fields | `private int trainer1TeamId;` and `private int trainer2TeamId;` with getters/setters. These get populated from `BattleCRUD.getBattleById()`. |
| **`getNextAvailablePokemon()`** | No change needed if using Option A above (the correct team is already loaded into `trainer.getTeam()`). |

#### `TurnManager.java` — No changes needed

Uses `trainer.getTeam()`, which under Option A already has the right team loaded.

---

### Layer 3 — Bot / Controller

#### `BotRunner.java` — Add `teamname` option to commands

| Command | Change |
|---------|--------|
| `/checkteam` | Add optional `teamname` string option (with autocomplete) |
| `/addpokemon` | Add required `teamname` string option (with autocomplete) |
| `/releasepokemon` | Add required `teamname` string option (with autocomplete) |
| `/startbattle` | Add optional `teamname` string option (with autocomplete) — selects which team to bring into battle |

The `/createteam` command already has `teamname` — no change needed.

#### `SlashExample.java` — Fix all 8 compile errors + add team resolution logic

Each command handler needs to **resolve the `teamId`** from user input before calling TeamCRUD. The pattern for every command:

```java
// 1. Get the team name from the command option (or use a default)
String teamName = event.getOption("teamname") != null 
    ? event.getOption("teamname").getAsString() 
    : null;

// 2. Resolve to a teamId using a new TeamCRUD method (see below)
int teamId = teamCRUD.getTeamIdByName(trainerId, teamName);
if (teamId == -1) {
    event.reply("Team not found! Use /createteam first.").setEphemeral(true).queue();
    return;
}

// 3. Pass teamId into every TeamCRUD call
teamCRUD.getDBTeamForTrainer(trainerId, teamId);
```

Specific fixes:

| Line | Command | Fix |
|------|---------|-----|
| 63 | `/startbattle` | Add team resolution, pass `teamId` to `getDBTeamForTrainer(trainerId, teamId)` |
| 105 | `/createteam` | Change `TeamCRUD.createTeamForTrainer(...)` → `teamCRUD.createTeamForTrainer(...)` (instance call, not static) |
| 123 | `/checkteam` | Add team resolution, pass `teamId` to `getDBTeamForTrainer(trainerId, teamId)` |
| 172 | `/addpokemon` | Add team resolution, pass `teamId` to `checkSlotIndex(trainerId, teamId)` |
| 192 | `/addpokemon` | Pass `teamId` to `addPokemonToDBTeam(trainerId, pokemonId, teamId)` |
| 222 | `/releasepokemon` | Pass `teamId` to `getSlotIndexForPokemon(trainerId, teamId, instanceId)` |
| 223 | `/releasepokemon` | Pass `teamId` to `removePokemonFromDBTeam(trainerId, teamId, slotIndex)` |
| 226 | `/releasepokemon` | Pass `teamId` to `reorderTeamAfterRelease(trainerId, teamId)` |

#### `AutoCompleteBot.java` — Fix 1 compile error + add team-name autocomplete

| Item | What to Change |
|------|---------------|
| **Line 94** | Pass `teamId` to `getDBTeamForTrainer(trainerId, teamId)` — but this requires knowing which team, so the autocomplete for `/releasepokemon` needs the `teamname` option filled first, or it could show Pokémon from all teams. |
| **New autocomplete** | Add autocomplete handler for the `teamname` option on `/checkteam`, `/addpokemon`, `/releasepokemon`. Query TeamCRUD for the trainer's team names. |

#### New TeamCRUD Method Needed

Add a method to look up a team's ID by name:

```java
public int getTeamIdByName(int trainerId, String teamName) {
    // SELECT team_id FROM trainer_teams 
    // WHERE trainer_id = ? AND team_name = ? 
    // LIMIT 1
}
```

Also consider a method to list all team names for a trainer (for autocomplete):

```java
public List<String> getTeamNamesForTrainer(int trainerId) {
    // SELECT DISTINCT team_name FROM trainer_teams 
    // WHERE trainer_id = ?
}
```

---

## Suggested Implementation Order

1. **Fix `checkSlotIndex()` bug** in TeamCRUD (the `team_name` in the log message) — quick fix, unblocks testing.
2. **Add `getTeamIdByName()` and `getTeamNamesForTrainer()`** to TeamCRUD — needed by the bot layer.
3. **Update BotRunner** — add `teamname` option to `/checkteam`, `/addpokemon`, `/releasepokemon`, `/startbattle`.
4. **Update SlashExample** — fix all 8 compile errors using the team-resolution pattern above.
5. **Update AutoCompleteBot** — fix the 1 compile error, add team-name autocomplete.
6. **Run the new schema** against MariaDB (drop + recreate `trainer_teams`).
7. **Update Database-Documentation.md** — sync the schema sections.
8. **Update `battles` table and BattleCRUD** — add `trainer1_team_id` / `trainer2_team_id` columns.
9. **Update `BattleService.createBattle()`** — accept and pass team IDs.
10. **Test** — verify `/createteam`, `/addpokemon`, `/checkteam`, `/releasepokemon` all work with team selection.

Steps 1–5 get the project compiling again. Steps 6–9 make the database and battle system consistent. Step 10 validates everything end-to-end.

---

## Design Decisions to Make

| Decision | Options | Recommendation |
|----------|---------|----------------|
| **Max teams per trainer** | 3? 5? Unlimited? | Start with 3. Add a check in `createTeamForTrainer()`. |
| **Default team** | Auto-create "Main" on `/createtrainer`? Require explicit `/createteam`? | Auto-create a "Main" team when a trainer is created to smooth the onboarding UX. |
| **Same Pokémon on multiple teams?** | Allow a single `instance_id` in multiple teams? Or enforce uniqueness? | Allow it — a trainer might want Pikachu on both their "PvP Team" and "Gym Team". The `UNIQUE` constraint is on `(trainer_id, team_name, slot_index)`, not on `instance_id`, so this already works. Just be careful during battles: if a Pokémon is in an active battle (HP is being modified), it shouldn't be usable in a second simultaneous battle. |
| **Team name uniqueness** | Case-sensitive? Case-insensitive? | Case-insensitive comparison when looking up by name (`WHERE LOWER(team_name) = LOWER(?)`) to avoid "Main" vs "main" confusion. |
| **What happens to "teamless" trainers?** | Existing trainers have data in the old schema with no `team_name`. | Migration script assigns them to a "Main" team. New trainers get a "Main" team auto-created. |
