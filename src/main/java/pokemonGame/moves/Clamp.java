package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Clamp extends Move {
    public static final Clamp INSTANCE = new Clamp();

    public Clamp() {
        super("Clamp", 35, Type.WATER,
        Category.PHYSICAL, 85, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Traps the target for 4-5 turns, dealing 1/8 of its max HP per turn.
    }
}
