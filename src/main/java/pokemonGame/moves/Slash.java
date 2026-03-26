package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Slash extends Move {
    public static final Slash INSTANCE = new Slash();

    public Slash() {
        super("Slash", 70, Type.NORMAL, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
