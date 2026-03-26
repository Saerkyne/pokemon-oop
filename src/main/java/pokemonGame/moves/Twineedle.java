package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Twineedle extends Move {
    public static final Twineedle INSTANCE = new Twineedle();

    public Twineedle() {
        super("Twineedle", 25, Type.BUG, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Hits the target twice. Each hit has a 20% chance to
        // poison the target.
    }
}
