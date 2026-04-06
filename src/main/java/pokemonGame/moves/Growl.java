package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Growl extends Move {
    public static final Growl INSTANCE = new Growl();

    public Growl() {
        super("Growl", 0, Type.NORMAL, Category.STATUS, 100, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's Attack by 1 stage.
    }
}
