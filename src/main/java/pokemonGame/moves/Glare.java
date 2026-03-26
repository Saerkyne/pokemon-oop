package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Glare extends Move {
    public static final Glare INSTANCE = new Glare();

    public Glare() {
        super("Glare", 0, Type.NORMAL, Category.STATUS, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Paralyzes the target.
    }
}
