package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Thrash extends Move {
    public static final Thrash INSTANCE = new Thrash();

    public Thrash() {
        super("Thrash", 120, Type.NORMAL, Category.PHYSICAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Attacks for 2-3 turns, then the user becomes confused.
    }
}
