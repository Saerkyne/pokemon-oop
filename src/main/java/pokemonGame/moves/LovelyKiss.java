package pokemonGame.moves;
import pokemonGame.Move;

public class LovelyKiss extends Move {
    public static final LovelyKiss INSTANCE = new LovelyKiss();

    public LovelyKiss() {
        super("Lovely Kiss", 0, "Normal", "Status", 75, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep.
    }
}
