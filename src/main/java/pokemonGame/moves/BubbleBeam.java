package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class BubbleBeam extends Move {
    public static final BubbleBeam INSTANCE = new BubbleBeam();

    public BubbleBeam() {
        super("Bubble Beam", 65, Type.WATER,
        Category.SPECIAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to lower the target's Speed by 1 stage.
    }

}
