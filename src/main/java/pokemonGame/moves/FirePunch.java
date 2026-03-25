package pokemonGame.moves;
import pokemonGame.Move;

public class FirePunch extends Move {
    public static final FirePunch INSTANCE = new FirePunch();

    public FirePunch() {
        super("Fire Punch", 75, "Fire", "Physical", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }
}
