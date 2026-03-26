package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class CometPunch extends Move {
    public static final CometPunch INSTANCE = new CometPunch();

    public CometPunch() {
        super("Comet Punch", 18, Type.NORMAL,
        Category.PHYSICAL, 85, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
