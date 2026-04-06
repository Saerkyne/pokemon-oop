package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Substitute extends Move {
    public static final Substitute INSTANCE = new Substitute();

    public Substitute() {
        super("Substitute", 0, Type.NORMAL, Category.STATUS, 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User sacrifices 25% of its max HP to create a decoy that
        // absorbs damage until it breaks.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
