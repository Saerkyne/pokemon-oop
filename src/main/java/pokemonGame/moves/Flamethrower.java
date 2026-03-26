package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Flamethrower extends Move {
    public static final Flamethrower INSTANCE = new Flamethrower();

    public Flamethrower() {
        super("Flamethrower", 90, Type.FIRE, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }
}
