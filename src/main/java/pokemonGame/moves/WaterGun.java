package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class WaterGun extends Move {
    public static final WaterGun INSTANCE = new WaterGun();

    public WaterGun() {
        super("Water Gun", 40, Type.WATER, Category.SPECIAL, 100, 25);
        // == Special Effect ==
        // No additional effect.
    }
}
