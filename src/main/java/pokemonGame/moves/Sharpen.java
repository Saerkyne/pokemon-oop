package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Sharpen extends Move {
    public static final Sharpen INSTANCE = new Sharpen();

    public Sharpen() {
        super("Sharpen", 0, Type.NORMAL, Category.STATUS, 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Attack by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
