package pokemonGame;

import java.sql.Timestamp;

/*
 * SUGGESTION: Record fields and persistence.
 *
 * Right now this record stores live Trainer and Pokemon objects. That works fine
 * for TurnManager (which needs them), but creates a mismatch with the database.
 * The battle_pending_actions table stores only:
 *   - battle_id, trainer_id, action_type='MOVE', move_slot_index
 *
 * Two valid approaches:
 *
 * Option A — Keep records lightweight (just IDs/indexes), let BattleService hydrate:
 *   public record MoveAction(int moveSlotIndex) implements BattleAction { }
 *   // BattleService loads Trainer + Pokemon from DB, then passes them
 *   // alongside the action to TurnManager.
 *
 * Option B — Keep live objects on the record (current approach), accept that these
 *   are in-memory-only objects. BattleTurnCRUD extracts moveSlotIndex for storage
 *   and reconstructs the full record when loading from DB.
 *
 * Either way, the record-to-DB mapping lives in BattleTurnCRUD, not here.
 */
public record MoveAction(Trainer trainer, Pokemon pokemon, int moveSlotIndex) implements BattleAction {

    public static final String ACTION_TYPE = "MOVE";

    @Override
    public String getActionType() {
        return ACTION_TYPE;
    }

    public int getMoveSlotIndex() {
        return moveSlotIndex;
    }

    /*
     * SUGGESTION: Remove getSubmittedAt() from this record.
     *
     * This is a persistence concern — "when was this action submitted" belongs
     * in the battle_pending_actions database row (submitted_at column), not on
     * the domain object. BattleTurnCRUD sets the timestamp when inserting the
     * row. TurnManager doesn't need to know when the action was submitted —
     * it only cares about what the action IS.
     *
     * Also, creating a new Timestamp(System.currentTimeMillis()) every time
     * this getter is called means it returns a different value each call —
     * which breaks the immutability contract records are meant to provide.
     */
    public Timestamp getSubmittedAt() {
        return new Timestamp(System.currentTimeMillis());
    }

    /*
     * SUGGESTION: Remove this method. Returning -1 for "not applicable" is the
     * exact pattern that sealed interfaces + pattern matching eliminate. After
     * slimming down BattleAction (see BattleAction.java comments), callers use
     * pattern matching to access type-specific data — they never call
     * getSwitchPokemonId() on a MoveAction because they already know it's a
     * MoveAction from the switch/case branch.
     */
    public int getSwitchPokemonId() {
        return -1; // Not applicable for MoveAction
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public Pokemon getCurrentPokemon() {
        return pokemon;
    }

    public MoveSlot getMoveSlot() {
        // This method can be implemented to return the MoveSlot being used for this MoveAction
        return pokemon.getMoveSet().get(moveSlotIndex);
    }

    public Move getMove() {
        // This method can be implemented to return the Move being used for this MoveAction
        return getMoveSlot().getMove();
    }

}
