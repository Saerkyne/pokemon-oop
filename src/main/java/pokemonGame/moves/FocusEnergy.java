package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class FocusEnergy extends Move {
    public static final FocusEnergy INSTANCE = new FocusEnergy();

    public FocusEnergy() {
        super("Focus Energy", 0, Type.NORMAL, Category.STATUS, 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Increases the user's critical-hit ratio by 2 stages.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
