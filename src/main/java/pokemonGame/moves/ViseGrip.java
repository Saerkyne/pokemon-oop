package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class ViseGrip extends Move {
    public static final ViseGrip INSTANCE = new ViseGrip();

    public ViseGrip() {
        super("Vise Grip", 55, Type.NORMAL, Category.PHYSICAL, 100, 30);
        // == Special Effect ==
        // No additional effect.
    }
}
