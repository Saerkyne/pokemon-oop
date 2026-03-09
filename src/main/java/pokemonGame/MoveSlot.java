package pokemonGame;

public class MoveSlot {
    private final Move move; // Immutable reference to the move assigned to this slot
    private int currentPP; // Mutable current PP for the move

    public MoveSlot(Move move) {
        this.move = move;
        this.currentPP = move.getMaxPp(); // Initialize current PP to max PP of the move
    }

    public void use() {
        if (currentPP > 0) {
            currentPP--; // Decrease PP by 1 when the move is used
        } else {
            throw new IllegalStateException("No PP left for this move!");
        }
    }

    public void restore() {
        currentPP = move.getMaxPp(); // Restore PP to max PP of the move
    }

    public int getCurrentPP() {
        return currentPP;
    }

    public Move getMove() {
        return move;
    }

}
