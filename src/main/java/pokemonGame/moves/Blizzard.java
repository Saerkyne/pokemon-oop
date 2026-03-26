package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Blizzard extends Move {
    public static final Blizzard INSTANCE = new Blizzard();

    public Blizzard() {
        super("Blizzard", 110, Type.ICE,
        Category.SPECIAL, 70, 5);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to freeze the target.
    }

}
