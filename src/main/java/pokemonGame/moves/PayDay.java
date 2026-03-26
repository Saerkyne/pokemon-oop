package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class PayDay extends Move {
    public static final PayDay INSTANCE = new PayDay();

    public PayDay() {
        super("Pay Day", 40, Type.NORMAL, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Scatters coins equal to 5x the user's level after battle.
    }
}
