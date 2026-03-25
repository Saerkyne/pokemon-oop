package pokemonGame.moves;
import pokemonGame.Move;

public class SleepPowder extends Move {
    public static final SleepPowder INSTANCE = new SleepPowder();

    public SleepPowder() {
        super("Sleep Powder", 0, "Grass", "Status", 75, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep. Does not affect Grass-type Pokemon.
        // Powder move — blocked by Overcoat.
    }
}
