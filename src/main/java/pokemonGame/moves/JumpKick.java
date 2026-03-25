package pokemonGame.moves;
import pokemonGame.Move;

public class JumpKick extends Move {
    public static final JumpKick INSTANCE = new JumpKick();

    public JumpKick() {
        super("Jump Kick", 100, "Fighting", "Physical", 95, 10);
        // == Special Effect (Not Yet Implemented) ==
        // If the move misses, the user takes crash damage equal to
        // half the damage that would have been dealt.
    }
}
