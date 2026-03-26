package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class IceBeam extends Move {
    public static final IceBeam INSTANCE = new IceBeam();

    public IceBeam() {
        super("Ice Beam", 90, Type.ICE, Category.SPECIAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to freeze the target.
    }
}
