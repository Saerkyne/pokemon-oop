package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Flash extends Move {
    public static final Flash INSTANCE = new Flash();

    public Flash() {
        super("Flash", 0, Type.NORMAL, Category.STATUS, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's accuracy by 1 stage.
    }
}
