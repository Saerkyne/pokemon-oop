package pokemonGame.moves;
import pokemonGame.Move;

public class ViseGrip extends Move {
    public static final ViseGrip INSTANCE = new ViseGrip();

    public ViseGrip() {
        super("Vise Grip", 55, "Normal", "Physical", 100, 30);
        // == Special Effect ==
        // No additional effect.
    }
}
