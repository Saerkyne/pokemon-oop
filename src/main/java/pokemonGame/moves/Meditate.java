package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Meditate extends Move {
    public static final Meditate INSTANCE = new Meditate();

    public Meditate() {
        super("Meditate", 0, Type.PSYCHIC, Category.STATUS, 0, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Attack by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
