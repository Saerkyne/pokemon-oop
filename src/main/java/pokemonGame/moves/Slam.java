package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Slam extends Move {
    public static final Slam INSTANCE = new Slam();

    public Slam() {
        super("Slam", 80, Type.NORMAL, Category.PHYSICAL, 75, 20);
        // == Special Effect ==
        // No additional effect.
    }
}
