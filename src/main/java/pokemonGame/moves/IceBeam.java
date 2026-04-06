package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class IceBeam extends Move {
    public static final IceBeam INSTANCE = new IceBeam();

    public IceBeam() {
        super("Ice Beam", 90, Type.ICE, Category.SPECIAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to freeze the target.
    }
}
