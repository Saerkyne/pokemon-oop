package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class WingAttack extends Move {
    public static final WingAttack INSTANCE = new WingAttack();

    public WingAttack() {
        super("Wing Attack", 60, Type.FLYING, Category.PHYSICAL, 100, 35);
        // == Special Effect ==
        // No additional effect.
    }
}
