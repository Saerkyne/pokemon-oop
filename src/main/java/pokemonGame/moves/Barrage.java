package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Barrage extends Move {
    public static final Barrage INSTANCE = new Barrage();

    public Barrage() {
        super("Barrage", 15, Type.NORMAL,
        Category.PHYSICAL, 85, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }

}
