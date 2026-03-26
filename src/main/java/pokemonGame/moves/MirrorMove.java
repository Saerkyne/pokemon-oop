package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class MirrorMove extends Move {
    public static final MirrorMove INSTANCE = new MirrorMove();

    public MirrorMove() {
        super("Mirror Move", 0, Type.FLYING, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Uses the last move that was used by the target.
        // Fails if the target hasn't used a move yet.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (depends on called move).
    }
}
