package pokemonGame.moves;
import pokemonGame.Move;

public class ConfuseRay extends Move {
    public static final ConfuseRay INSTANCE = new ConfuseRay();

    public ConfuseRay() {
        super("Confuse Ray", 0, "Ghost",
        "Status", 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Causes confusion on the target.
    }
}
