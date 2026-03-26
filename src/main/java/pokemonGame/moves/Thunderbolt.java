package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Thunderbolt extends Move {
    public static final Thunderbolt INSTANCE = new Thunderbolt();

    public Thunderbolt() {
        super("Thunderbolt", 90, Type.ELECTRIC, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to paralyze the target.
    }
}
