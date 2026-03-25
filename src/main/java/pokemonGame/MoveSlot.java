package pokemonGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveSlot {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveSlot.class);
    private final Move move; // Immutable reference to the move assigned to this slot
    private int currentPP; // Mutable current PP for the move

    public MoveSlot(Move move) {
        this.move = move;
        this.currentPP = move.getMaxPp(); // Initialize current PP to max PP of the move
    }

    public boolean use() {
        if (currentPP > 0) {
            currentPP--; // Decrease PP by 1 when the move is used
            return true;
        } else {
            LOGGER.info("No PP left for move: " + move.getMoveName());
            return false;
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
