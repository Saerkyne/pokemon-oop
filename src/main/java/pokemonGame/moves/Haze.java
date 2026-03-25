package pokemonGame.moves;
import pokemonGame.Move;

public class Haze extends Move {
    public static final Haze INSTANCE = new Haze();

    public Haze() {
        super("Haze", 0, "Ice", "Status", 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Resets all stat changes for all active Pokemon to zero.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (affects the field).
    }
}
