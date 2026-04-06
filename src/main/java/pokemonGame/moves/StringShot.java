package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class StringShot extends Move {
    public static final StringShot INSTANCE = new StringShot();

    public StringShot() {
        super("String Shot", 0, Type.BUG, Category.STATUS, 95, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's Speed by 2 stages.
    }
}
