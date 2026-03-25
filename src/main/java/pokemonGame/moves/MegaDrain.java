package pokemonGame.moves;
import pokemonGame.Move;

public class MegaDrain extends Move {
    public static final MegaDrain INSTANCE = new MegaDrain();

    public MegaDrain() {
        super("Mega Drain", 40, "Grass", "Special", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // User recovers 50% of the damage dealt to the target.
    }
}
