package pokemonGame.moves;
import pokemonGame.Move;

public class WaterGun extends Move {
    public static final WaterGun INSTANCE = new WaterGun();

    public WaterGun() {
        super("Water Gun", 40, "Water", "Special", 100, 25);
        // == Special Effect ==
        // No additional effect.
    }
}
