package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Conversion extends Move {
    public static final Conversion INSTANCE = new Conversion();

    public Conversion() {
        super("Conversion", 0, Type.NORMAL,
        Category.STATUS, 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Changes the user's type to the same type as one of its
        // moves, selected at random.
    }
}
