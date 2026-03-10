package pokemonGame.moves;
import pokemonGame.Move;

public class HighJumpKick extends Move {
    public HighJumpKick() {
        super("High Jump Kick", 130, "Fighting", "Physical", 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // If the move misses, the user takes crash damage equal to
        // half the damage that would have been dealt.
    }
}
