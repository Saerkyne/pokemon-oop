package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class DizzyPunch extends Move {
    public static final DizzyPunch INSTANCE = new DizzyPunch();

    public DizzyPunch() {
        super("Dizzy Punch", 70, Type.NORMAL, Category.PHYSICAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 20% chance to confuse the target.
    }
}
