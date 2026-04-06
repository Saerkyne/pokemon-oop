package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Counter extends Move {
    public static final Counter INSTANCE = new Counter();

    public Counter() {
        super("Counter", 0, Type.FIGHTING,
        Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // If the user is hit by a physical attack this turn, it deals
        // double the damage received back to the attacker. Priority -5.
    }
}
