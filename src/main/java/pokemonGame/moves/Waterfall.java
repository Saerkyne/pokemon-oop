package pokemonGame.moves;
import pokemonGame.Move;

public class Waterfall extends Move {
    public static final Waterfall INSTANCE = new Waterfall();

    public Waterfall() {
        super("Waterfall", 80, "Water", "Physical", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 20% chance to make the target flinch.
    }
}
