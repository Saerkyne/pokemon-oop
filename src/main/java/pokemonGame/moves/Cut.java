package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Cut extends Move {
    public static final Cut INSTANCE = new Cut();

    public Cut() {
        super("Cut", 50, Type.NORMAL,
        Category.PHYSICAL, 95, 30);
        // == Special Effect ==
        // No additional effect.
    }
}
