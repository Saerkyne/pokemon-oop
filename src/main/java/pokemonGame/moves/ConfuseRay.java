package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class ConfuseRay extends Move {
    public static final ConfuseRay INSTANCE = new ConfuseRay();

    public ConfuseRay() {
        super("Confuse Ray", 0, Type.GHOST,
        Category.STATUS, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Causes confusion on the target.
    }
}
