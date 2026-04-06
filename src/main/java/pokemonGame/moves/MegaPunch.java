package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class MegaPunch extends Move {
    public static final MegaPunch INSTANCE = new MegaPunch();

    public MegaPunch() {
        super("Mega Punch", 20, Type.NORMAL, Category.PHYSICAL, 85, 20);
        // == Special Effect ==
        // No additional effect.
    }
}
