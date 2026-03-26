package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class RockThrow extends Move {
    public static final RockThrow INSTANCE = new RockThrow();

    public RockThrow() {
        super("Rock Throw", 50, Type.ROCK, Category.PHYSICAL, 90, 15);
        // == Special Effect ==
        // No additional effect.
    }
}
