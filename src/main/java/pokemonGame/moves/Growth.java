package pokemonGame.moves;
import pokemonGame.Move;

public class Growth extends Move {
    public Growth() {
        super("Growth", 0, "Normal", "Status", 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Attack and Sp. Atk by 1 stage each.
        // In harsh sunlight, raises both by 2 stages instead.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
