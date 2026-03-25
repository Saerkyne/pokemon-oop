package pokemonGame.moves;
import pokemonGame.Move;

public class MegaPunch extends Move {
    public static final MegaPunch INSTANCE = new MegaPunch();

    public MegaPunch() {
        super("Mega Punch", 20, "Normal", "Physical", 85, 20);
        // == Special Effect ==
        // No additional effect.
    }
}
