package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Bide extends Move {
    public static final Bide INSTANCE = new Bide();

    public Bide() {
        super("Bide", 0, Type.NORMAL,
        Category.PHYSICAL, 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User endures attacks for 2 turns, then strikes back with
        // double the damage received.
        // Accuracy: 0 is a placeholder — PokemonDB lists "—" (always hits).
    }

}
