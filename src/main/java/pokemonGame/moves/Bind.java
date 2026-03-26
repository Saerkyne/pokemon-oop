package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Bind extends Move {
    public static final Bind INSTANCE = new Bind();

    public Bind() {
        super("Bind", 15, Type.NORMAL,
        Category.PHYSICAL, 85, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Traps the target for 4-5 turns, dealing 1/8 of its max HP per turn.
    }

}
