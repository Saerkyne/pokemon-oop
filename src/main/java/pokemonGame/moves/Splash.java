package pokemonGame.moves;
import pokemonGame.Move;

public class Splash extends Move {
    public static final Splash INSTANCE = new Splash();

    public Splash() {
        super("Splash", 0, "Normal", "Status", 0, 40);
        // == Special Effect ==
        // No effect whatsoever. The move does nothing.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
