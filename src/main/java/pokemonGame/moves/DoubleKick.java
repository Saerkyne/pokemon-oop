package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class DoubleKick extends Move {
    public static final DoubleKick INSTANCE = new DoubleKick();

    public DoubleKick() {
        super("Double Kick", 30, Type.FIGHTING, Category.PHYSICAL, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Hits the target twice in one turn.
    }
}
