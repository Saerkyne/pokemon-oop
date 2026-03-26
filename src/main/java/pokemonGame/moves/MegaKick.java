package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class MegaKick extends Move {
    public static final MegaKick INSTANCE = new MegaKick();

    public MegaKick() {
        super("Mega Kick", 120, Type.NORMAL, Category.PHYSICAL, 75, 5);
        // == Special Effect ==
        // No additional effect.
    }
}
