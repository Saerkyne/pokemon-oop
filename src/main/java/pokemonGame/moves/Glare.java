package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Glare extends Move {
    public static final Glare INSTANCE = new Glare();

    public Glare() {
        super("Glare", 0, Type.NORMAL, Category.STATUS, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Paralyzes the target.
    }
}
