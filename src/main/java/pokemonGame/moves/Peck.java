package pokemonGame.moves;
import pokemonGame.Move;

public class Peck extends Move {
    public static final Peck INSTANCE = new Peck();

    public Peck() {
        super("Peck", 35, "Flying", "Physical", 100, 35);
        // == Special Effect ==
        // No additional effect.
    }
}
