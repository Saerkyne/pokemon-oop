package pokemonGame.moves;
import pokemonGame.Move;

public class ThunderPunch extends Move {
    public static final ThunderPunch INSTANCE = new ThunderPunch();

    public ThunderPunch() {
        super("Thunder Punch", 75, "Electric", "Physical", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to paralyze the target.
    }
}
