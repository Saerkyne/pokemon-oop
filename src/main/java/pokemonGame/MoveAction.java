package pokemonGame;

/**
 * A player's choice to use a move during a battle turn.
 * Immutable record — captures the trainer, their active Pokémon, and which
 * move slot (0–3) was selected.
 *
 * @see BattleAction
 * @see SwitchAction
 */
public record MoveAction(Trainer trainer, Pokemon pokemon, int moveSlotIndex) implements BattleAction {

    public int getMoveSlotIndex() {
        return moveSlotIndex;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public Pokemon activePokemon() {
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

    public String getActionType() {
        return "MOVE";
    }   

}
