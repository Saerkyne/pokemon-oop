package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class MegaKick extends Move {
    public static final MegaKick INSTANCE = new MegaKick();

    public MegaKick() {
        super("Mega Kick", 120, Type.NORMAL, Category.PHYSICAL, 75, 5);
        // == Special Effect ==
        // No additional effect.
    }
}
