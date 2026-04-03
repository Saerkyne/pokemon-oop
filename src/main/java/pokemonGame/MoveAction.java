package pokemonGame;

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
