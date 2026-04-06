package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Slam extends Move {
    public static final Slam INSTANCE = new Slam();

    public Slam() {
        super("Slam", 80, Type.NORMAL, Category.PHYSICAL, 75, 20);
        // == Special Effect ==
        // No additional effect.
    }
}
