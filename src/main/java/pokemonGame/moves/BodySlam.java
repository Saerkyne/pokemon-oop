package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class BodySlam extends Move {
    public static final BodySlam INSTANCE = new BodySlam();

    public BodySlam() {
        super("Body Slam", 85, Type.NORMAL,
        Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to paralyze the target.
    }

}
