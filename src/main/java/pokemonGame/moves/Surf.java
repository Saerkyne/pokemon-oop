package pokemonGame.moves;
import pokemonGame.Move;

public class Surf extends Move {
    public static final Surf INSTANCE = new Surf();

    public Surf() {
        super("Surf", 90, "Water", "Special", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // In double battles, hits all adjacent Pokemon.
        // Can hit a target using Dive for double damage.
    }
}
