package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class PinMissile extends Move {
    public static final PinMissile INSTANCE = new PinMissile();

    public PinMissile() {
        super("Pin Missile", 25, Type.BUG, Category.PHYSICAL, 95, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
