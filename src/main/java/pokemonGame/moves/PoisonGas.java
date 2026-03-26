package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class PoisonGas extends Move {
    public static final PoisonGas INSTANCE = new PoisonGas();

    public PoisonGas() {
        super("Poison Gas", 0, Type.POISON, Category.STATUS, 90, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Poisons the target.
    }
}
