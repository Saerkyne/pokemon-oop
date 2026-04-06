package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Confusion extends Move {
    public static final Confusion INSTANCE = new Confusion();

    public Confusion() {
        super("Confusion", 50, Type.PSYCHIC,
        Category.SPECIAL, 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to confuse the target.
    }
}
