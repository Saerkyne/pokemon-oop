package pokemonGame.moves;
import pokemonGame.Move;

public class Strength extends Move {
    public static final Strength INSTANCE = new Strength();

    public Strength() {
        super("Strength", 15, "Normal", "Physical", 80, 15);
        // == Special Effect ==
        // No additional effect.
    }
}
