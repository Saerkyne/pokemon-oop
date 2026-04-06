package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Bonemerang extends Move {
    public static final Bonemerang INSTANCE = new Bonemerang();

    public Bonemerang() {
        super("Bonemerang", 50, Type.GROUND,
        Category.PHYSICAL, 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Hits the target twice in one turn.
    }

}
