package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class HyperBeam extends Move {
    public static final HyperBeam INSTANCE = new HyperBeam();

    public HyperBeam() {
        super("Hyper Beam", 150, Type.NORMAL, Category.SPECIAL, 90, 5);
        // == Special Effect (Not Yet Implemented) ==
        // User must recharge on the next turn and cannot act.
    }
}
