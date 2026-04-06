package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class MegaDrain extends Move {
    public static final MegaDrain INSTANCE = new MegaDrain();

    public MegaDrain() {
        super("Mega Drain", 40, Type.GRASS, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // User recovers 50% of the damage dealt to the target.
    }
}
