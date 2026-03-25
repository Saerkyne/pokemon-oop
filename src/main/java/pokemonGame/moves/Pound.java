package pokemonGame.moves;
import pokemonGame.Move;

public class Pound extends Move {
    public static final Pound INSTANCE = new Pound();

    public Pound() {
        super("Pound", 40, "Normal", "Physical", 100, 35);
        // == Special Effect ==
        // No additional effect.
    }
}
