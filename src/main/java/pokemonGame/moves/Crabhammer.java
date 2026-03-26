package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Crabhammer extends Move {
    public static final Crabhammer INSTANCE = new Crabhammer();

    public Crabhammer() {
        super("Crabhammer", 100, Type.WATER,
        Category.PHYSICAL, 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
