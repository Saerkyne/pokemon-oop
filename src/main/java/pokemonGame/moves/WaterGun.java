package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class WaterGun extends Move {
    public static final WaterGun INSTANCE = new WaterGun();

    public WaterGun() {
        super("Water Gun", 40, Type.WATER, Category.SPECIAL, 100, 25);
        // == Special Effect ==
        // No additional effect.
    }
}
