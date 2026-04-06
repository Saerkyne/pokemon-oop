package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class DoubleKick extends Move {
    public static final DoubleKick INSTANCE = new DoubleKick();

    public DoubleKick() {
        super("Double Kick", 30, Type.FIGHTING, Category.PHYSICAL, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Hits the target twice in one turn.
    }
}
