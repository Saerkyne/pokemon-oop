package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class RockThrow extends Move {
    public static final RockThrow INSTANCE = new RockThrow();

    public RockThrow() {
        super("Rock Throw", 50, Type.ROCK, Category.PHYSICAL, 90, 15);
        // == Special Effect ==
        // No additional effect.
    }
}
