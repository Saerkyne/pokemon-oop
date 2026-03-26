package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Growth extends Move {
    public static final Growth INSTANCE = new Growth();

    public Growth() {
        super("Growth", 0, Type.NORMAL, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Attack and Sp. Atk by 1 stage each.
        // In harsh sunlight, raises both by 2 stages instead.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
