package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

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
