package pokemonGame.model;

/**
 * Wraps a {@link Move} with mutable PP tracking. Each Pokémon's moveset is
 * a list of up to 4 MoveSlots. The underlying Move is immutable (game-rule
 * data); the MoveSlot tracks per-instance PP consumption.
 *
 * @see Move
 * @see Pokemon
 */
public class MoveSlot {

    
    private final Move move; // Immutable reference to the move assigned to this slot
    private int currentPP; // Mutable current PP for the move

    public MoveSlot(Move move) {
        this.move = move;
        this.currentPP = move.getMaxPp(); // Initialize current PP to max PP of the move
    }

    public int getCurrentPP() {
        return currentPP;
    }

    public Move getMove() {
        return move;
    }

    public void setCurrentPP(int currentPP) {
        if (currentPP < 0 || currentPP > move.getMaxPp()) {
            throw new IllegalArgumentException("Current PP must be between 0 and max PP of the move.");
        }
        this.currentPP = currentPP;
    }

    

}
