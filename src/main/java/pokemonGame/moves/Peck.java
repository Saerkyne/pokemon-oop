package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Peck extends Move {
    public static final Peck INSTANCE = new Peck();

    public Peck() {
        super("Peck", 35, Type.FLYING, Category.PHYSICAL, 100, 35);
        // == Special Effect ==
        // No additional effect.
    }
}
