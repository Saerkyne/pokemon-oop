package pokemonGame.moves;
import pokemonGame.Move;

public class Slash extends Move {
    public static final Slash INSTANCE = new Slash();

    public Slash() {
        super("Slash", 70, "Normal", "Physical", 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
