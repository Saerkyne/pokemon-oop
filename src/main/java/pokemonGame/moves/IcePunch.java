package pokemonGame.moves;
import pokemonGame.Move;

public class IcePunch extends Move {
    public static final IcePunch INSTANCE = new IcePunch();

    public IcePunch() {
        super("Ice Punch", 75, "Ice", "Physical", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to freeze the target.
    }
}
