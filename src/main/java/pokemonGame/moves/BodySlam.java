package pokemonGame.moves;
import pokemonGame.Move;

public class BodySlam extends Move {
    public static final BodySlam INSTANCE = new BodySlam();

    public BodySlam() {
        super("Body Slam", 85, "Normal",
        "Physical", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to paralyze the target.
    }

}
