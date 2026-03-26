package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class VineWhip extends Move {
    public static final VineWhip INSTANCE = new VineWhip();

    public VineWhip() {
        super("Vine Whip", 45, Type.GRASS, Category.PHYSICAL, 100, 25);
        // == Special Effect ==
        // No additional effect.
    }

}
