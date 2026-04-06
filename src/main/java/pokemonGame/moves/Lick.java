package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Lick extends Move {
    public static final Lick INSTANCE = new Lick();

    public Lick() {
        super("Lick", 30, Type.GHOST, Category.PHYSICAL, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to paralyze the target.
    }
}
