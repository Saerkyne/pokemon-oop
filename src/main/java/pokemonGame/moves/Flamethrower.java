package pokemonGame.moves;
import pokemonGame.Move;

public class Flamethrower extends Move {
    public static final Flamethrower INSTANCE = new Flamethrower();

    public Flamethrower() {
        super("Flamethrower", 90, "Fire", "Special", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }
}
