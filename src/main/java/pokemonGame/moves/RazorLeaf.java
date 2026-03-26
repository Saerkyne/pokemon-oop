package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class RazorLeaf extends Move {
    public static final RazorLeaf INSTANCE = new RazorLeaf();

    public RazorLeaf() {
        super("Razor Leaf", 55, Type.GRASS, Category.PHYSICAL, 95, 25);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
