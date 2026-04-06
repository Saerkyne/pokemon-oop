package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class VineWhip extends Move {
    public static final VineWhip INSTANCE = new VineWhip();

    public VineWhip() {
        super("Vine Whip", 45, Type.GRASS, Category.PHYSICAL, 100, 25);
        // == Special Effect ==
        // No additional effect.
    }

}
