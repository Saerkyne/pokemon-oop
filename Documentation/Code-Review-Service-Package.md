# Code Review — `pokemonGame.service` Package

**Date:** 2026-04-09  
**Files reviewed:** `TrainerService.java`, `TeamService.java`, `BattleService.java`, `MoveSlotService.java`

---

## Summary

The service layer is 4 files, ~545 lines total. It correctly sits between the bot layer and the DAO/model layers, coordinating multi-step workflows. `MoveSlotService` and `BattleService` accept DAO dependencies through constructor injection (good), while `TrainerService` and `TeamService` create them internally (fixable). The most severe issue is two crash bugs where `TeamService` calls `.add()` and `.remove()` on an unmodifiable list returned by `Team.getTeamAsList()`.

---

## Issues

### SVC-1 · BUG · `addPokemonToTeam()` crashes — calls `.add()` on unmodifiable list

**File:** `TeamService.java`, line 178

```java
team.getTeamAsList().add(pokemon);
```

`Team.getTeamAsList()` returns `Collections.unmodifiableList(teamAsList)`. Calling `.add()` on this always throws `UnsupportedOperationException`. The Pokémon is persisted to the database (steps 1 and 2 succeed), but the in-memory update crashes — leaving the DB and in-memory state out of sync.

**Why this matters (educational context):** `Collections.unmodifiableList()` returns a read-only **view** of the original list. It's designed to prevent exactly this kind of external mutation. The `Team` class exposes `add()` and `remove()` methods specifically for modifying the internal list safely.

**Fix:**

```java
team.add(pokemon);
```

---

### SVC-2 · BUG · `releasePokemon()` crashes — calls `.remove()` on unmodifiable list

**File:** `TeamService.java`, line 211

```java
team.getTeamAsList().remove(pokemon);
```

Same issue as SVC-1. The DB operations succeed (remove from `team_members`, delete from `pokemon_instances`, reorder slots), but the final in-memory update crashes.

**Fix:**

```java
team.remove(pokemon);
```

---

### SVC-3 · DESIGN · `TrainerService` and `TeamService` create DAO instances internally

**File:** `TrainerService.java`, lines 50–52; `TeamService.java`, lines 61–63

```java
// TrainerService
public TrainerService() {
    this.trainerCRUD = new TrainerCRUD();
}

// TeamService
public TeamService() {
    this.teamCRUD = new TeamCRUD();
    this.pokemonCRUD = new PokemonCRUD();
}
```

`BattleService` and `MoveSlotService` correctly accept their DAO through the constructor (`new BattleService(BattleCRUD battleCrud)`). `TrainerService` and `TeamService` create their DAOs internally with `new`, which means:

1. Unit tests cannot inject mock/stub DAOs
2. The service is tightly coupled to the concrete DAO implementation
3. It's inconsistent with the other two services

**Fix — add constructor injection (keep default constructor for convenience):**

```java
public TrainerService(TrainerCRUD trainerCRUD) {
    this.trainerCRUD = trainerCRUD;
}

public TrainerService() {
    this(new TrainerCRUD());
}
```

---

### SVC-4 · BUG · `MoveSlotService.teachMove()` doesn't check if moveset is full

**File:** `MoveSlotService.java`, lines 50–66

```java
if (moveCRUD.insertMoveForPokemon(p.getPokemonDbId(), p.getMoveSet().size(), move.getMoveName(), move.getMaxPp()) == -1) {
```

If the Pokémon already has 4 moves, `p.getMoveSet().size()` returns 4. The DB insert uses `slot_index = 4`, which violates the `CHECK (slot_index BETWEEN 0 AND 3)` constraint. Then `p.addMove(move)` returns `false` silently (moveset full). Result: DB operation either fails with a constraint violation or inserts an invalid slot, while the in-memory moveset is unchanged.

**Why this matters:** The `teachMove` method doesn't account for the "moveset full, player must choose which move to forget" flow. It only handles the "open slot" case.

**Fix — guard at the top:**

```java
public void teachMove(Pokemon p, Move move) {
    if (p.isMovesetFull()) {
        LOGGER.warn("Cannot teach {}; {}'s moveset is full. Use replaceMove instead.",
            move.getMoveName(), p.getNickname());
        return;
    }
    // ... rest of method
}
```

---

### SVC-5 · ROBUSTNESS · `MoveSlotService.getMoveByName()` propagates uncaught exception

**File:** `MoveSlotService.java`, lines 70–72

```java
public Move getMoveByName(String moveName) {
    return PokeMove.fromString(moveName).createMove();
}
```

`PokeMove.fromString()` throws `IllegalArgumentException` if the move name doesn't match any enum constant. The caller gets an unhandled exception. Since this may be called with user input from Discord (autocomplete typos, manual entry), it should handle invalid names gracefully.

**Fix:**

```java
public Move getMoveByName(String moveName) {
    try {
        return PokeMove.fromString(moveName).createMove();
    } catch (IllegalArgumentException e) {
        LOGGER.warn("Unknown move name: '{}'", moveName);
        return null;
    }
}
```

---

### SVC-6 · DESIGN · `BattleService.createActionFromDbRow()` is a stub returning null

**File:** `BattleService.java`, lines 25–47

```java
public static BattleAction createActionFromDbRow(...) {
    return null;
}
```

`BattleTurnCRUD.getPendingActions()` calls this method to rehydrate `BattleAction` records from database rows. Since it always returns `null`, pending actions can never be loaded — the battle turn loop cannot function.

**Not an immediate fix** — this is documented as work-in-progress. Included here for tracking.

---

### SVC-7 · DESIGN · `createBattle()` and `createChallenge()` are near-duplicate methods

**File:** `BattleService.java`, lines 51–88

Both methods:

1. Check for an existing active battle between the two trainers
2. Call `battleCrud.createBattle()` with status `"PENDING"`
3. Return `true`/`false`

The only difference is that `createBattle()` passes real team IDs while `createChallenge()` passes `-1, -1`. This could be a single method with an optional team-ID parameter, or `createChallenge()` could call `createBattle()` with sentinel values.

---

## Stats

| Severity     | Count |
|:-------------|:-----:|
| BUG          |   3   |
| DESIGN       |   3   |
| ROBUSTNESS   |   1   |
| **Total**    | **7** |
