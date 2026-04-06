package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Supersonic extends Move {
    public static final Supersonic INSTANCE = new Supersonic();

    public Supersonic() {
        super("Supersonic", 0, Type.NORMAL, Category.STATUS, 55, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Causes confusion on the target. Sound-based move.
    }
}
