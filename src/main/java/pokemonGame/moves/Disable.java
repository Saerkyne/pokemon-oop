package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Disable extends Move {
    public static final Disable INSTANCE = new Disable();

    public Disable() {
        super("Disable", 0, Type.NORMAL, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Disables the target's last used move for 4 turns,
        // preventing it from being used.
    }

}
