package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class HornAttack extends Move {
    public static final HornAttack INSTANCE = new HornAttack();

    public HornAttack() {
        super("Horn Attack", 65, Type.NORMAL, Category.PHYSICAL, 100, 25);
        // == Special Effect ==
        // No additional effect.
    }
}
