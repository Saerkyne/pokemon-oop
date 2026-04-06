package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class RazorLeaf extends Move {
    public static final RazorLeaf INSTANCE = new RazorLeaf();

    public RazorLeaf() {
        super("Razor Leaf", 55, Type.GRASS, Category.PHYSICAL, 95, 25);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
