package pokemonGame.moves;
import pokemonGame.Move;

public class HydroPump extends Move {
    public static final HydroPump INSTANCE = new HydroPump();

    public HydroPump() {
        super("Hydro Pump", 110, "Water", "Special", 80, 5);
        // == Special Effect ==
        // No additional effect.
    }
}
