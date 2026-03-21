## Project Checklist — Pokemon-OOP (Granular, Per-File Tracker)

This checklist breaks down the project into actionable, per-file TODOs and phases. Use it to track progress, PRs, and implementation status. Mark items as [ ] (open) or [x] (done).

---

## 1. Discovery & Review
- [x] Review `README.md` for project goals and usage.
- [x] Review `Documentation/Database-Documentation.md` for DB schema and persistence.
- [ ] Review all test files in `src/test/java/pokemonGame/` for intended behaviors and open questions.
- [ ] Identify and document any open questions (e.g., HP on stat recalc).

## 2. Test Suite & Signals
- [ ] Run all tests in `src/test/java/pokemonGame/`.
- [ ] List all failing/disabled tests as spec items to address.

## 3. Core Implementation — Battle & HP
### `src/main/java/pokemonGame/Battle.java`
- [ ] Implement battle loop (turn order, round structure).
- [ ] Implement turn ordering (speed, priority).
- [ ] Apply damage to `Pokemon.currentHP` using `Attack.calculateDamage`.
- [ ] Clamp HP to [0, maxHP]; handle fainting.
- [ ] Integrate move selection and execution.

### `src/main/java/pokemonGame/Pokemon.java`
- [ ] Update methods to support HP clamping and fainting.
- [ ] Ensure `currentHP` is decremented and tracked.
- [ ] Add/clarify fainted state and recovery.

### `src/main/java/pokemonGame/Attack.java`
- [ ] Confirm damage calculation matches intended mechanics (STAB, crits, random factor).
- [ ] Ensure effectiveness and type logic is correct.

## 4. Core Implementation — Stat Recalculation
### `src/main/java/pokemonGame/Pokemon.java`
- [ ] Decide: stat recalc fully heals or preserves HP proportionally?
- [ ] Update `calculateCurrentStats()` to implement chosen behavior.
- [ ] Add/clarify documentation for stat recalc and HP.

## 5. Database Integration
### `Documentation/Database-Documentation.md`
- [ ] Finalize SQL schema and migration scripts.

### `src/main/java/pokemonGame/db/DatabaseSetup.java`
- [ ] Ensure DB setup and migration logic is complete.

### `src/main/java/pokemonGame/db/PokemonCRUD.java`
- [ ] Implement all CRUD operations for Pokémon.
- [ ] Add tests for CRUD methods.

### `src/main/java/pokemonGame/db/TeamCRUD.java`
- [ ] Implement all CRUD operations for teams.
- [ ] Add tests for CRUD methods.

### `src/main/java/pokemonGame/db/TrainerCRUD.java`
- [ ] Implement all CRUD operations for trainers.
- [ ] Add tests for CRUD methods.

### `src/main/java/pokemonGame/App.java`
- [ ] Implement load/save hooks for DB integration.
- [ ] Ensure persistence of `currentHP`, PP, IV/EV, level, nature, etc.

## 6. Moves & Effects
### `src/main/java/pokemonGame/moves/`
- [ ] Implement all Gen 1 moves as classes.
- [ ] For each move flagged as TODO, implement special/secondary effects.
- [ ] Add unit tests for representative moves (healing, multi-hit, status chance).

## 7. I/O & UX
### `src/main/java/pokemonGame/App.java`
- [ ] Centralize all console I/O in controller layer.
- [ ] Refactor `LearnsetEntry` and `Pokemon.addMove()` to accept controller decisions (no direct input).
- [ ] Implement `selectFromAvailablePokemon` CLI.
- [ ] Improve prompts and error handling for user input.

## 8. Status Effects & Stat Stages
### `src/main/java/pokemonGame/Battle.java`
- [ ] Add status effect resolution (burn, poison, paralysis, etc.).
- [ ] Add stat stage mechanics (boosts/drops).
- [ ] Integrate statuses and stat stages into turn resolution.

### `src/main/java/pokemonGame/Pokemon.java`
- [ ] Track and update status conditions and stat stages.

## 9. Tests & Re-enable
### `src/test/java/pokemonGame/`
- [ ] Re-enable and expand tests as features are implemented.
- [ ] Add tests for new mechanics (battle, statuses, DB round-trip).

## 10. Polish & Documentation
### `src/main/java/pokemonGame/db/`
- [ ] Add transactional DB saves and connection pooling.

### `README.md`
- [ ] Add usage snippets and update documentation.

### `Documentation/Project-Checklist.md`
- [ ] Keep this checklist up to date as tasks are completed.

---

## Verification Steps
- [ ] All tests pass (`mvn -q test`).
- [ ] Manual CLI run: verify battle, HP reduction, fainting, and DB persistence.
- [ ] Unit tests for moves (multi-hit, drain, status chance) pass.
- [ ] Database round-trip: save/load trainer+team, verify all fields.

---

## Notes & Open Questions
- [ ] Should stat recalculation fully heal or preserve HP proportionally?
- [ ] Any additional Gen 1 mechanics to clarify?
- [ ] Further granularity needed for any file or feature?

---

*Update this file as you work. Use it for PR checklists and progress tracking.*
Priority order: 1) Database, 2) User Interaction (Console + Discord bot), 3) Battle system, 4) Connection pooling / concurrency.

Each phase contains concrete tasks, file pointers, verification steps, and suggested test cases.

---

## Phase 1 — Database (Priority 1)
- [ ] Finalize SQL schema and migration scripts
  - Files/refs: [Documentation/Database-Documentation.md](Documentation/Database-Documentation.md)
  - Deliverable: SQL scripts for `trainers`, `pokemon_instances`, `pokemon_movesets`, `trainer_teams`, and optional `type_chart`.
- [ ] Add seed data scripts (small dataset for dev/test)
  - Deliverable: `sql/seed.sql` with example trainer, 1–2 teams, and 5 Pokémon instances.
- [ ] Implement CRUD operations
  - `PokemonCRUD.java`: save/load instance rows, hydrate species objects, persist IV/EV/level/nature/current_hp
  - `TeamCRUD.java`: save/load a trainer's team slots
  - `TrainerCRUD.java`: create/read trainer records
  - Files: [src/main/java/pokemonGame/db/PokemonCRUD.java](src/main/java/pokemonGame/db/PokemonCRUD.java), [src/main/java/pokemonGame/db/TeamCRUD.java](src/main/java/pokemonGame/db/TeamCRUD.java), [src/main/java/pokemonGame/db/TrainerCRUD.java](src/main/java/pokemonGame/db/TrainerCRUD.java)
- [ ] DB integration tests
  - Location: `src/test/java/pokemonGame/` (new DB tests)
  - Tests: round-trip save/load of a Pokemon instance (IV/EV/level/nature/currentHP/PP), team save/load, transaction rollback on failure
- [ ] Implement transactional save/load flows in `App` and CRUD classes
  - Ensure saving a team + moveset is atomic
  - Files: [src/main/java/pokemonGame/App.java](src/main/java/pokemonGame/App.java), DB package
- [ ] Centralize DB connection management (pre-HikariCP)
  - Single `DataSourceProvider` abstraction to swap in pooling later
  - Files: `src/main/java/pokemonGame/db/DataSourceProvider.java`

Verification steps:
- Execute migration scripts to a local dev DB, run seed script, run integration tests.
- Manual: create trainer, save team, reload, verify values match.

---

## Phase 2 — User Interaction (Priority 2)
Goals: two-pronged I/O model — Console for local testing, Discord bot for production.

A. Console (Testing)
- [ ] Centralize console I/O in `App` (single `Scanner` / input loop)
  - Remove or refactor `System.console()` usage scattered in domain classes
  - Files: [src/main/java/pokemonGame/App.java](src/main/java/pokemonGame/App.java)
- [ ] Implement `selectFromAvailablePokemon` CLI helper
  - Provide keyboard navigation, numeric selection, validation
- [ ] Create a `ConsoleTestHarness` mode in `App` to run scripted scenarios
  - Use this to exercise load/save and battle flows locally

B. Discord Bot (Production)
- [ ] Design Discord command interface (slash commands)
  - Commands: `/battle start`, `/battle action <payload>`, `/battle state`, `/trainer load`, `/trainer save`
  - Document payloads and response format (ephemeral vs public)
- [ ] Implement battle state serialization/deserialization
  - Deliverable: JSON schema for `BattleState` capturing trainers, Pokémon instances (IDs), currentHP, PP, statuses, turn order
  - Files: add `src/main/java/pokemonGame/net/BattleStateSerializer.java`
- [ ] Implement a lightweight adapter (Discord integration project)
  - This can be a separate Java app that uses the same model classes (or a shared library jar)
  - Responsibilities: receive slash commands, call CRUD to rehydrate state, present state to user, accept next action, persist updates
- [ ] Define concurrency model for per-user/per-battle state persistence
  - Use the DB to persist state between slash commands; ensure atomic saves

Verification steps:
- Console: run `ConsoleTestHarness` and manually step through save/load and basic battle turns.
- Discord: run the adapter locally (mock slash events) and verify round-trip serialization.

---

## Phase 3 — Battle System (Priority 3)
- [ ] Implement battle loop and turn ordering
  - File: [src/main/java/pokemonGame/Battle.java](src/main/java/pokemonGame/Battle.java)
  - Features: action queue, speed-based ordering, tie-breakers, priority moves
- [ ] Damage application and HP clamping
  - Ensure `Attack.calculateDamage()` result is applied, HP clamped to [0, maxHP], fainted state set
  - Files: `Battle.java`, `Pokemon.java`, `Attack.java`
- [ ] Status effects and stat stages
  - Implement burn/poison/paralysis/sleep/freeze, stat stage modifiers (+/-6 stages)
- [ ] Secondary move effects and multi-hit moves
  - Implement move-level flags or hooks for effects (drain, recoil, status chance, variable hits)
  - Files: `src/main/java/pokemonGame/moves/*`
- [ ] AI trainer move selection
  - Simple heuristic: prefer highest expected damage, avoid fainting self, prefer status moves when useful
- [ ] Battle unit tests
  - Deterministic tests for turn order, multi-hit, drain, status application, faint handling

Verification steps:
- Re-enable and pass tests in `src/test/java/pokemonGame/BattleTest.java` and related tests.
- Manual: start console battle and verify correct outcomes.

---

## Phase 4 — Connection Pooling & Concurrency (Priority 4)
- [ ] Add HikariCP connection pooling
  - Add dependency and configure pool settings via `DataSourceProvider`
  - Files: `pom.xml`, `src/main/java/pokemonGame/db/DataSourceProvider.java`
- [ ] Ensure CRUD methods use pooled `DataSource` and are thread-safe
- [ ] Implement per-battle locking or optimistic concurrency as needed
  - Consider `version` column on `pokemon_instances` or `battle_sessions` table
- [ ] Load test multiple simultaneous battles
  - Simulate many concurrent Discord interactions recreating and saving battle state

Verification steps:
- Run a small load test to simulate N concurrent battle saves/loads and confirm no deadlocks or lost updates.

---

## Cross-cutting Tasks
- [ ] Logging and error handling
  - Centralize logging config (logback.xml exists). Ensure DB errors are logged and bubble up cleanly.
- [ ] Documentation updates and examples
  - Update `README.md` with how to run console mode, how to run migrations, and Discord adapter notes
- [ ] Tests: incrementally unskip/enable tests as features are implemented
- [ ] Code style and formatting
  - Run formatter or IDE settings to keep code consistent

---

## Suggested Milestones (for releases)
- Milestone Alpha: DB schema + CRUD + Console save/load (no battle) — verify round-trip persistence
- Milestone Beta: Console battle loop working end-to-end (no Discord), core moves implemented
- Milestone Release Candidate: Discord adapter prototype + battle state serialization + HikariCP

---

## Files to Review / Edit (quick links)
- [Documentation/Database-Documentation.md](Documentation/Database-Documentation.md)
- [src/main/java/pokemonGame/App.java](src/main/java/pokemonGame/App.java)
- [src/main/java/pokemonGame/Battle.java](src/main/java/pokemonGame/Battle.java)
- [src/main/java/pokemonGame/Pokemon.java](src/main/java/pokemonGame/Pokemon.java)
- [src/main/java/pokemonGame/db/PokemonCRUD.java](src/main/java/pokemonGame/db/PokemonCRUD.java)
- [src/main/java/pokemonGame/db/TeamCRUD.java](src/main/java/pokemonGame/db/TeamCRUD.java)

---

