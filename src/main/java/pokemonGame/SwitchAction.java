package pokemonGame;

import java.sql.Timestamp;

/*
 * SUGGESTION: Same persistence considerations as MoveAction — see MoveAction.java.
 * The database stores only: battle_id, trainer_id, action_type='SWITCH', switch_pokemon_id.
 * BattleTurnCRUD handles the mapping between this record and the DB row.
 */
public record SwitchAction(Trainer trainer, Pokemon pokemon, int teamSlotIndex) implements BattleAction {
    
    public static final String ACTION_TYPE = "SWITCH";

    @Override
    public String getActionType() {
        return ACTION_TYPE;
    }

    // SUGGESTION: Remove — same reasoning as MoveAction. Timestamp belongs in BattleTurnCRUD.
    public Timestamp getSubmittedAt() {
        return new Timestamp(System.currentTimeMillis());
    }

    // SUGGESTION: Remove — same reasoning as MoveAction.getSwitchPokemonId().
    // Sentinel value (-1) for "not applicable" goes away when callers use pattern matching.
    public int getMoveSlotIndex() {
        return -1; // Not applicable for SwitchAction
    }

    public int getSwitchPokemonId() {
        return teamSlotIndex;
    }

    /*
     * SUGGESTION: This method takes teamSlotIndex as a parameter, but the record
     * already has teamSlotIndex as a field. It also needs a Trainer's team list to
     * do the lookup. Consider making this work off the record's own fields:
     *
     *   public Pokemon switchTarget() {
     *       return trainer.getTeam().get(teamSlotIndex);
     *   }
     *
     * This is the same pattern resolveSwitch() in TurnManager already uses.
     * Alternatively, let TurnManager.resolveSwitch() do the lookup itself — it
     * already has access to the trainer and the index. Then this method is
     * unnecessary and can be removed.
     */
    public Pokemon getNewPokemon(int teamSlotIndex) {
        // This method can be implemented to return the new Pokémon being switched in based on the team slot index

        return null; // Placeholder implementation
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public Pokemon getCurrentPokemon() {
        return pokemon;
    }
}
