package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Gust extends Move {
    public static final Gust INSTANCE = new Gust();

    public Gust() {
        super("Gust", 40, Type.FLYING, Category.SPECIAL, 100, 35);
        // == Special Effect (Not Yet Implemented) ==
        // Can hit a target using Fly, dealing double damage.
    }
}
