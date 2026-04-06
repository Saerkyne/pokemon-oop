package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class CometPunch extends Move {
    public static final CometPunch INSTANCE = new CometPunch();

    public CometPunch() {
        super("Comet Punch", 18, Type.NORMAL,
        Category.PHYSICAL, 85, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
