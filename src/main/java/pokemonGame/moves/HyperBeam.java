package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class HyperBeam extends Move {
    public static final HyperBeam INSTANCE = new HyperBeam();

    public HyperBeam() {
        super("Hyper Beam", 150, Type.NORMAL, Category.SPECIAL, 90, 5);
        // == Special Effect (Not Yet Implemented) ==
        // User must recharge on the next turn and cannot act.
    }
}
