package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Sludge extends Move {
    public static final Sludge INSTANCE = new Sludge();

    public Sludge() {
        super("Sludge", 65, Type.POISON, Category.SPECIAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to poison the target.
    }
}
