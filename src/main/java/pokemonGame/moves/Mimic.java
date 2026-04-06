package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Mimic extends Move {
    public static final Mimic INSTANCE = new Mimic();

    public Mimic() {
        super("Mimic", 0, Type.NORMAL, Category.STATUS, 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Copies one of the target's moves; the user can use it
        // for the rest of the battle, replacing Mimic in its moveset.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (always succeeds).
    }
}
