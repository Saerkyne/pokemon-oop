package pokemonGame.moves;
import pokemonGame.Move;

public class Hypnosis extends Move {
    public static final Hypnosis INSTANCE = new Hypnosis();

    public Hypnosis() {
        super("Hypnosis", 0, "Psychic", "Status", 60, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep.
    }
}
