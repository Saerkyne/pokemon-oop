package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class IcePunch extends Move {
    public static final IcePunch INSTANCE = new IcePunch();

    public IcePunch() {
        super("Ice Punch", 75, Type.ICE, Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to freeze the target.
    }
}
