package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Bubble extends Move {
    public static final Bubble INSTANCE = new Bubble();

    public Bubble() {
        super("Bubble", 40, Type.WATER,
        Category.SPECIAL, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to lower the target's Speed by 1 stage.
    }

}
