package pokemonGame.moves;
import pokemonGame.Move;

public class Explosion extends Move {
    public static final Explosion INSTANCE = new Explosion();

    public Explosion() {
        super("Explosion", 250, "Normal", "Physical", 100, 5);
        // == Special Effect (Not Yet Implemented) ==
        // User faints after using this move.
    }
}
