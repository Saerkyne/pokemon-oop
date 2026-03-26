package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Thrash extends Move {
    public static final Thrash INSTANCE = new Thrash();

    public Thrash() {
        super("Thrash", 120, Type.NORMAL, Category.PHYSICAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Attacks for 2-3 turns, then the user becomes confused.
    }
}
