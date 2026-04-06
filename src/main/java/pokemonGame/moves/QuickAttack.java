package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;


public class QuickAttack extends Move {
    public static final QuickAttack INSTANCE = new QuickAttack();

    public QuickAttack() {
        super("Quick Attack", 40, Type.NORMAL, Category.PHYSICAL, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Priority +1. This move always goes before most other moves.
    }
}
