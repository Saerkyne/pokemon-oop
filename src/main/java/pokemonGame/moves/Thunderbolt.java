package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Thunderbolt extends Move {
    public static final Thunderbolt INSTANCE = new Thunderbolt();

    public Thunderbolt() {
        super("Thunderbolt", 90, Type.ELECTRIC, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to paralyze the target.
    }
}
