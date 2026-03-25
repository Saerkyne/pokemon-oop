package pokemonGame.moves;
import pokemonGame.Move;

public class RockThrow extends Move {
    public static final RockThrow INSTANCE = new RockThrow();

    public RockThrow() {
        super("Rock Throw", 50, "Rock", "Physical", 90, 15);
        // == Special Effect ==
        // No additional effect.
    }
}
