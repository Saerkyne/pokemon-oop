package pokemonGame.moves;
import pokemonGame.Move;

public class BubbleBeam extends Move {
    public static final BubbleBeam INSTANCE = new BubbleBeam();

    public BubbleBeam() {
        super("Bubble Beam", 65, "Water",
        "Special", 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to lower the target's Speed by 1 stage.
    }

}
