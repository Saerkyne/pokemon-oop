package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Haze extends Move {
    public static final Haze INSTANCE = new Haze();

    public Haze() {
        super("Haze", 0, Type.ICE, Category.STATUS, 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Resets all stat changes for all active Pokemon to zero.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (affects the field).
    }
}
