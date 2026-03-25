package pokemonGame.moves;
import pokemonGame.Move;

public class VineWhip extends Move {
    public static final VineWhip INSTANCE = new VineWhip();

    public VineWhip() {
        super("Vine Whip", 45, "Grass", "Physical", 100, 25);
        // == Special Effect ==
        // No additional effect.
    }

}
