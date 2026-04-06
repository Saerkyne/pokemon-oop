package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Constrict extends Move {
    public static final Constrict INSTANCE = new Constrict();

    public Constrict() {
        super("Constrict", 10, Type.NORMAL,
        Category.PHYSICAL, 100, 35);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to lower the target's Speed by 1 stage.
    }
}
