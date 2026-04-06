package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Recover extends Move {
    public static final Recover INSTANCE = new Recover();

    public Recover() {
        super("Recover", 0, Type.NORMAL, Category.STATUS, 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Restores up to 50% of the user's maximum HP.
    }

}
