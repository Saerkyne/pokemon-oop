package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Clamp extends Move {
    public static final Clamp INSTANCE = new Clamp();

    public Clamp() {
        super("Clamp", 35, Type.WATER,
        Category.PHYSICAL, 85, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Traps the target for 4-5 turns, dealing 1/8 of its max HP per turn.
    }
}
