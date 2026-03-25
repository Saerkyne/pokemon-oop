package pokemonGame.moves;
import pokemonGame.Move;

public class TailWhip extends Move {
    public static final TailWhip INSTANCE = new TailWhip();

    public TailWhip() {
        super("Tail Whip", 0, "Normal", "Status", 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's Defense by 1 stage.
    }
}
