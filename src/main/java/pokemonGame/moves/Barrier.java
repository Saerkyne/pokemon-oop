package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Barrier extends Move {
    public static final Barrier INSTANCE = new Barrier();

    public Barrier() {
        super("Barrier", 0, Type.PSYCHIC,
        Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Defense by 2 stages.
    }

}
