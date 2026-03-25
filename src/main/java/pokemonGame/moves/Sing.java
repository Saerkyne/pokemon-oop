package pokemonGame.moves;
import pokemonGame.Move;

public class Sing extends Move {
    public static final Sing INSTANCE = new Sing();

    public Sing() {
        super("Sing", 0, "Normal", "Status", 55, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep. Sound-based move.
    }
}
