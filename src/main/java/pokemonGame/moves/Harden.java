package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Harden extends Move {
    public static final Harden INSTANCE = new Harden();

    public Harden() {
        super("Harden", 0, Type.NORMAL, Category.STATUS, 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Defense by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
