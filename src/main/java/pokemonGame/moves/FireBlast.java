package pokemonGame.moves;
import pokemonGame.Move;

public class FireBlast extends Move {
    public static final FireBlast INSTANCE = new FireBlast();

    public FireBlast() {
        super("Fire Blast", 110, "Fire", "Special", 85, 5);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }
}
