package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class BodySlam extends Move {
    public static final BodySlam INSTANCE = new BodySlam();

    public BodySlam() {
        super("Body Slam", 85, Type.NORMAL,
        Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to paralyze the target.
    }

}
