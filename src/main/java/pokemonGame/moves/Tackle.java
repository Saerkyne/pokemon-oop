package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Tackle extends Move {
    public static final Tackle INSTANCE = new Tackle();

    public Tackle() {
        super("Tackle", 40, Type.NORMAL, Category.PHYSICAL, 100, 35);
        // == Special Effect ==
        // No additional effect.
    }

}
