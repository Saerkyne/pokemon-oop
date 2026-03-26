package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Explosion extends Move {
    public static final Explosion INSTANCE = new Explosion();

    public Explosion() {
        super("Explosion", 250, Type.NORMAL, Category.PHYSICAL, 100, 5);
        // == Special Effect (Not Yet Implemented) ==
        // User faints after using this move.
    }
}
