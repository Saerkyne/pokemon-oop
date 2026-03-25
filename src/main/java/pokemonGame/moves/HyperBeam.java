package pokemonGame.moves;
import pokemonGame.Move;

public class HyperBeam extends Move {
    public static final HyperBeam INSTANCE = new HyperBeam();

    public HyperBeam() {
        super("Hyper Beam", 150, "Normal", "Special", 90, 5);
        // == Special Effect (Not Yet Implemented) ==
        // User must recharge on the next turn and cannot act.
    }
}
