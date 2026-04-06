package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Blizzard extends Move {
    public static final Blizzard INSTANCE = new Blizzard();

    public Blizzard() {
        super("Blizzard", 110, Type.ICE,
        Category.SPECIAL, 70, 5);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to freeze the target.
    }

}
