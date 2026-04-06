package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Teleport extends Move {
    public static final Teleport INSTANCE = new Teleport();

    public Teleport() {
        super("Teleport", 0, Type.PSYCHIC, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Flees from wild battles.
        // In trainer battles, switches the user out. Priority -6.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
