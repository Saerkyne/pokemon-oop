package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Flamethrower extends Move {
    public static final Flamethrower INSTANCE = new Flamethrower();

    public Flamethrower() {
        super("Flamethrower", 90, Type.FIRE, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }
}
