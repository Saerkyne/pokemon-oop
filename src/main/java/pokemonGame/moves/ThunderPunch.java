package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class ThunderPunch extends Move {
    public static final ThunderPunch INSTANCE = new ThunderPunch();

    public ThunderPunch() {
        super("Thunder Punch", 75, Type.ELECTRIC, Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to paralyze the target.
    }
}
