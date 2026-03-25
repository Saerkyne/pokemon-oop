package pokemonGame.moves;
import pokemonGame.Move;

public class Harden extends Move {
    public static final Harden INSTANCE = new Harden();

    public Harden() {
        super("Harden", 0, "Normal", "Status", 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Defense by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
