package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class PetalDance extends Move {
    public static final PetalDance INSTANCE = new PetalDance();

    public PetalDance() {
        super("Petal Dance", 120, Type.GRASS, Category.SPECIAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Attacks for 2-3 turns, then the user becomes confused.
    }
}
