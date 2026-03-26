package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class StringShot extends Move {
    public static final StringShot INSTANCE = new StringShot();

    public StringShot() {
        super("String Shot", 0, Type.BUG, Category.STATUS, 95, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's Speed by 2 stages.
    }
}
