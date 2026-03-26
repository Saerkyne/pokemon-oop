package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class DoubleEdge extends Move {
    public static final DoubleEdge INSTANCE = new DoubleEdge();

    public DoubleEdge() {
        super("Double-Edge", 120, Type.NORMAL, Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // User takes 33% of the damage dealt as recoil.
    }
}
