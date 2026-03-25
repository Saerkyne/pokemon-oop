package pokemonGame.moves;
import pokemonGame.Move;

public class DoubleSlap extends Move {
    public static final DoubleSlap INSTANCE = new DoubleSlap();

    public DoubleSlap() {
        super("Double Slap", 15, "Normal", "Physical", 85, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
