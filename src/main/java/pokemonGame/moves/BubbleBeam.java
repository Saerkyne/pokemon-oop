package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class BubbleBeam extends Move {
    public static final BubbleBeam INSTANCE = new BubbleBeam();

    public BubbleBeam() {
        super("Bubble Beam", 65, Type.WATER,
        Category.SPECIAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to lower the target's Speed by 1 stage.
    }

}
