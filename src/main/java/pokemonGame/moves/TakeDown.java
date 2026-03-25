package pokemonGame.moves;
import pokemonGame.Move;

public class TakeDown extends Move {
    public static final TakeDown INSTANCE = new TakeDown();

    public TakeDown() {
        super("Take Down", 90, "Normal", "Physical", 85, 20);
        // == Special Effect (Not Yet Implemented) ==
        // User takes 25% of the damage dealt as recoil.
    }
}
