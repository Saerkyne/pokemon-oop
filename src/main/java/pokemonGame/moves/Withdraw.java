package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Withdraw extends Move {
    public static final Withdraw INSTANCE = new Withdraw();

    public Withdraw() {
        super("Withdraw", 0, Type.WATER, Category.STATUS, 0, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Defense by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
