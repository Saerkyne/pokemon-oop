package pokemonGame.moves;
import pokemonGame.Move;

public class PoisonGas extends Move {
    public static final PoisonGas INSTANCE = new PoisonGas();

    public PoisonGas() {
        super("Poison Gas", 0, "Poison", "Status", 90, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Poisons the target.
    }
}
