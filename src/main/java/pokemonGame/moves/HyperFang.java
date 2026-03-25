package pokemonGame.moves;
import pokemonGame.Move;

public class HyperFang extends Move {
    public static final HyperFang INSTANCE = new HyperFang();

    public HyperFang() {
        super("Hyper Fang", 80, "Normal", "Physical", 90, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to make the target flinch.
    }
}
