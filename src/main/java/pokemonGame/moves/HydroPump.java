package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class HydroPump extends Move {
    public static final HydroPump INSTANCE = new HydroPump();

    public HydroPump() {
        super("Hydro Pump", 110, Type.WATER, Category.SPECIAL, 80, 5);
        // == Special Effect ==
        // No additional effect.
    }
}
