package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class HornAttack extends Move {
    public static final HornAttack INSTANCE = new HornAttack();

    public HornAttack() {
        super("Horn Attack", 65, Type.NORMAL, Category.PHYSICAL, 100, 25);
        // == Special Effect ==
        // No additional effect.
    }
}
