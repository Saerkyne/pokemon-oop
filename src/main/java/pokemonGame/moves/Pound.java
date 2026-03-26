package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Pound extends Move {
    public static final Pound INSTANCE = new Pound();

    public Pound() {
        super("Pound", 40, Type.NORMAL, Category.PHYSICAL, 100, 35);
        // == Special Effect ==
        // No additional effect.
    }
}
