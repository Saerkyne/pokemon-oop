package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class FireBlast extends Move {
    public static final FireBlast INSTANCE = new FireBlast();

    public FireBlast() {
        super("Fire Blast", 110, Type.FIRE, Category.SPECIAL, 85, 5);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }
}
