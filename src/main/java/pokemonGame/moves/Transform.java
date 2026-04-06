package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Transform extends Move {
    public static final Transform INSTANCE = new Transform();

    public Transform() {
        super("Transform", 0, Type.NORMAL, Category.STATUS, 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User transforms into the target, copying its species, type,
        // moves (each set to 5 PP), stats, and stat changes.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (always succeeds).
    }
}
