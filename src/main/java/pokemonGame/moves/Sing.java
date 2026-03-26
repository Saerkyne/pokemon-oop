package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Sing extends Move {
    public static final Sing INSTANCE = new Sing();

    public Sing() {
        super("Sing", 0, Type.NORMAL, Category.STATUS, 55, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep. Sound-based move.
    }
}
