package pokemonGame.moves;
import pokemonGame.Move;

public class MirrorMove extends Move {
    public static final MirrorMove INSTANCE = new MirrorMove();

    public MirrorMove() {
        super("Mirror Move", 0, "Flying", "Status", 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Uses the last move that was used by the target.
        // Fails if the target hasn't used a move yet.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (depends on called move).
    }
}
