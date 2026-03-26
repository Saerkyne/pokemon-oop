package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class HighJumpKick extends Move {
    public static final HighJumpKick INSTANCE = new HighJumpKick();

    public HighJumpKick() {
        super("High Jump Kick", 130, Type.FIGHTING, Category.PHYSICAL, 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // If the move misses, the user takes crash damage equal to
        // half the damage that would have been dealt.
    }
}
