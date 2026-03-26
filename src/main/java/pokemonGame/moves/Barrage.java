package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Barrage extends Move {
    public static final Barrage INSTANCE = new Barrage();

    public Barrage() {
        super("Barrage", 15, Type.NORMAL,
        Category.PHYSICAL, 85, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }

}
