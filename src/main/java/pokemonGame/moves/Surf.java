package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Surf extends Move {
    public static final Surf INSTANCE = new Surf();

    public Surf() {
        super("Surf", 90, Type.WATER, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // In double battles, hits all adjacent Pokemon.
        // Can hit a target using Dive for double damage.
    }
}
