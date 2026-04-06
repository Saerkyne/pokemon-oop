package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class RazorWind extends Move {
    public static final RazorWind INSTANCE = new RazorWind();

    public RazorWind() {
        super("Razor Wind", 80, Type.NORMAL, Category.SPECIAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Charges on turn 1, attacks on turn 2. High critical-hit ratio.
    }
}
