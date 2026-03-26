package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Withdraw extends Move {
    public static final Withdraw INSTANCE = new Withdraw();

    public Withdraw() {
        super("Withdraw", 0, Type.WATER, Category.STATUS, 0, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Defense by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
