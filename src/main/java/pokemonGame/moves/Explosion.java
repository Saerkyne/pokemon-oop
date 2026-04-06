package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Explosion extends Move {
    public static final Explosion INSTANCE = new Explosion();

    public Explosion() {
        super("Explosion", 250, Type.NORMAL, Category.PHYSICAL, 100, 5);
        // == Special Effect (Not Yet Implemented) ==
        // User faints after using this move.
    }
}
