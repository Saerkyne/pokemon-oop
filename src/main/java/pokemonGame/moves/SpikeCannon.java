package pokemonGame.moves;
import pokemonGame.Move;

public class SpikeCannon extends Move {
    public static final SpikeCannon INSTANCE = new SpikeCannon();

    public SpikeCannon() {
        super("Spike Cannon", 20, "Normal", "Physical", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
