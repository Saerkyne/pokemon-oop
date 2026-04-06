package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class FirePunch extends Move {
    public static final FirePunch INSTANCE = new FirePunch();

    public FirePunch() {
        super("Fire Punch", 75, Type.FIRE, Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }
}
