package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class IcePunch extends Move {
    public static final IcePunch INSTANCE = new IcePunch();

    public IcePunch() {
        super("Ice Punch", 75, Type.ICE, Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to freeze the target.
    }
}
