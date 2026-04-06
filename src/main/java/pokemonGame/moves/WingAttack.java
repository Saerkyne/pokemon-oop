package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class WingAttack extends Move {
    public static final WingAttack INSTANCE = new WingAttack();

    public WingAttack() {
        super("Wing Attack", 60, Type.FLYING, Category.PHYSICAL, 100, 35);
        // == Special Effect ==
        // No additional effect.
    }
}
