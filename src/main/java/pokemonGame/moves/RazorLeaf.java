package pokemonGame.moves;
import pokemonGame.Move;

public class RazorLeaf extends Move {
    public static final RazorLeaf INSTANCE = new RazorLeaf();

    public RazorLeaf() {
        super("Razor Leaf", 55, "Grass", "Physical", 95, 25);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
