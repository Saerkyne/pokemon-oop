package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Strength extends Move {
    public static final Strength INSTANCE = new Strength();

    public Strength() {
        super("Strength", 15, Type.NORMAL, Category.PHYSICAL, 80, 15);
        // == Special Effect ==
        // No additional effect.
    }
}
