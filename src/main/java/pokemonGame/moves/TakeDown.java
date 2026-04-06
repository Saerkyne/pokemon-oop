package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class TakeDown extends Move {
    public static final TakeDown INSTANCE = new TakeDown();

    public TakeDown() {
        super("Take Down", 90, Type.NORMAL, Category.PHYSICAL, 85, 20);
        // == Special Effect (Not Yet Implemented) ==
        // User takes 25% of the damage dealt as recoil.
    }
}
