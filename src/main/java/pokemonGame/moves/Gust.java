package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Gust extends Move {
    public static final Gust INSTANCE = new Gust();

    public Gust() {
        super("Gust", 40, Type.FLYING, Category.SPECIAL, 100, 35);
        // == Special Effect (Not Yet Implemented) ==
        // Can hit a target using Fly, dealing double damage.
    }
}
