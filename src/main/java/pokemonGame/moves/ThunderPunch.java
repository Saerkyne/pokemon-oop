package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class ThunderPunch extends Move {
    public static final ThunderPunch INSTANCE = new ThunderPunch();

    public ThunderPunch() {
        super("Thunder Punch", 75, Type.ELECTRIC, Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to paralyze the target.
    }
}
