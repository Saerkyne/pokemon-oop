package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class LightScreen extends Move {
    public static final LightScreen INSTANCE = new LightScreen();

    public LightScreen() {
        super("Light Screen", 0, Type.PSYCHIC, Category.STATUS, 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Reduces special damage taken by the user's team by 50% for 5 turns.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
