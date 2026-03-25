package pokemonGame.moves;
import pokemonGame.Move;

public class AuroraBeam extends Move {
    public static final AuroraBeam INSTANCE = new AuroraBeam();

    public AuroraBeam() {
        super("Aurora Beam", 65, "Ice",
        "Special", 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to lower the target's Attack by 1 stage.
    }

}
