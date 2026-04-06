package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class TriAttack extends Move {
    public static final TriAttack INSTANCE = new TriAttack();

    public TriAttack() {
        super("Tri Attack", 80, Type.NORMAL, Category.SPECIAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 20% chance to inflict burn, freeze, or paralysis
        // (6.67% chance of each).
    }
}
