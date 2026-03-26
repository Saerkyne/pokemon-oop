package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class MegaPunch extends Move {
    public static final MegaPunch INSTANCE = new MegaPunch();

    public MegaPunch() {
        super("Mega Punch", 20, Type.NORMAL, Category.PHYSICAL, 85, 20);
        // == Special Effect ==
        // No additional effect.
    }
}
