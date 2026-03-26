package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class ViseGrip extends Move {
    public static final ViseGrip INSTANCE = new ViseGrip();

    public ViseGrip() {
        super("Vise Grip", 55, Type.NORMAL, Category.PHYSICAL, 100, 30);
        // == Special Effect ==
        // No additional effect.
    }
}
