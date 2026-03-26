package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Wrap extends Move {
    public static final Wrap INSTANCE = new Wrap();

    public Wrap() {
        super("Wrap", 15, Type.NORMAL, Category.PHYSICAL, 90, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Traps the target for 4-5 turns, dealing 1/8 of its max HP per turn.
    }
}
