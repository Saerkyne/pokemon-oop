package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class SwordsDance extends Move {
    public static final SwordsDance INSTANCE = new SwordsDance();

    public SwordsDance() {
        super("Swords Dance", 0, Type.NORMAL, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Attack by 2 stages.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
