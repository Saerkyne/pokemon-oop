package pokemonGame.moves;
import pokemonGame.Move;

public class DizzyPunch extends Move {
    public static final DizzyPunch INSTANCE = new DizzyPunch();

    public DizzyPunch() {
        super("Dizzy Punch", 70, "Normal", "Physical", 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 20% chance to confuse the target.
    }
}
