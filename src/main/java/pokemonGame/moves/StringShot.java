package pokemonGame.moves;
import pokemonGame.Move;

public class StringShot extends Move {
    public static final StringShot INSTANCE = new StringShot();

    public StringShot() {
        super("String Shot", 0, "Bug", "Status", 95, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's Speed by 2 stages.
    }
}
