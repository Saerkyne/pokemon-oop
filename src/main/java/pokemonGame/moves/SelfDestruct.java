package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class SelfDestruct extends Move {
    public static final SelfDestruct INSTANCE = new SelfDestruct();

    public SelfDestruct() {
        super("Self-Destruct", 200, Type.NORMAL, Category.PHYSICAL, 100, 5);
        // == Special Effect (Not Yet Implemented) ==
        // User faints after using this move.
    }
}
