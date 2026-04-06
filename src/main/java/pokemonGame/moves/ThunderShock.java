package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class ThunderShock extends Move {
    public static final ThunderShock INSTANCE = new ThunderShock();

    public ThunderShock() {
        super("ThunderShock", 40, Type.ELECTRIC, Category.SPECIAL, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to paralyze the target.
    }

}
