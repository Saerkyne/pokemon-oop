package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class PoisonGas extends Move {
    public static final PoisonGas INSTANCE = new PoisonGas();

    public PoisonGas() {
        super("Poison Gas", 0, Type.POISON, Category.STATUS, 90, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Poisons the target.
    }
}
