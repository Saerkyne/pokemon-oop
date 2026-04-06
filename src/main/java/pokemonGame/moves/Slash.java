package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Slash extends Move {
    public static final Slash INSTANCE = new Slash();

    public Slash() {
        super("Slash", 70, Type.NORMAL, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
