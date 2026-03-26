package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class FirePunch extends Move {
    public static final FirePunch INSTANCE = new FirePunch();

    public FirePunch() {
        super("Fire Punch", 75, Type.FIRE, Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }
}
