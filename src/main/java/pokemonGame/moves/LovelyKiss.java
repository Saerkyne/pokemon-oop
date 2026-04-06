package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class LovelyKiss extends Move {
    public static final LovelyKiss INSTANCE = new LovelyKiss();

    public LovelyKiss() {
        super("Lovely Kiss", 0, Type.NORMAL, Category.STATUS, 75, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep.
    }
}
