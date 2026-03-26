package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Bonemerang extends Move {
    public static final Bonemerang INSTANCE = new Bonemerang();

    public Bonemerang() {
        super("Bonemerang", 50, Type.GROUND,
        Category.PHYSICAL, 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Hits the target twice in one turn.
    }

}
