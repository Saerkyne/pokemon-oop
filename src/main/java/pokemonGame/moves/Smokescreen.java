package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Smokescreen extends Move {
    public static final Smokescreen INSTANCE = new Smokescreen();

    public Smokescreen() {
        super("Smokescreen", 0, Type.NORMAL, Category.STATUS, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's accuracy by 1 stage.
    }
}
