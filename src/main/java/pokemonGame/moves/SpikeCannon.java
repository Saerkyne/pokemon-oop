package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class SpikeCannon extends Move {
    public static final SpikeCannon INSTANCE = new SpikeCannon();

    public SpikeCannon() {
        super("Spike Cannon", 20, Type.NORMAL, Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
