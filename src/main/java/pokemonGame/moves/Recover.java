package pokemonGame.moves;
import pokemonGame.Move;

public class Recover extends Move {
    public static final Recover INSTANCE = new Recover();

    public Recover() {
        super("Recover", 0, "Normal", "Status", 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Restores up to 50% of the user's maximum HP.
    }

}
