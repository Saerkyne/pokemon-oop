package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class LovelyKiss extends Move {
    public static final LovelyKiss INSTANCE = new LovelyKiss();

    public LovelyKiss() {
        super("Lovely Kiss", 0, Type.NORMAL, Category.STATUS, 75, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep.
    }
}
