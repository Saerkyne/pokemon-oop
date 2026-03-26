package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Earthquake extends Move {
    public static final Earthquake INSTANCE = new Earthquake();

    public Earthquake() {
        super("Earthquake", 100, Type.GROUND, Category.PHYSICAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Can hit a target using Dig for double damage.
        // In double battles, hits all adjacent Pokemon.
    }
}
