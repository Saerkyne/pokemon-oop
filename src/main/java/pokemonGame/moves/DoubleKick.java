package pokemonGame.moves;
import pokemonGame.Move;

public class DoubleKick extends Move {
    public static final DoubleKick INSTANCE = new DoubleKick();

    public DoubleKick() {
        super("Double Kick", 30, "Fighting", "Physical", 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Hits the target twice in one turn.
    }
}
