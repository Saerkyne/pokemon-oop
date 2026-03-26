package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class PinMissile extends Move {
    public static final PinMissile INSTANCE = new PinMissile();

    public PinMissile() {
        super("Pin Missile", 25, Type.BUG, Category.PHYSICAL, 95, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
