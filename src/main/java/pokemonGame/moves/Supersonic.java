package pokemonGame.moves;
import pokemonGame.Move;

public class Supersonic extends Move {
    public static final Supersonic INSTANCE = new Supersonic();

    public Supersonic() {
        super("Supersonic", 0, "Normal", "Status", 55, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Causes confusion on the target. Sound-based move.
    }
}
