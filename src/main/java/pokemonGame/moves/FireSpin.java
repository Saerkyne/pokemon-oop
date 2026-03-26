package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class FireSpin extends Move {
    public static final FireSpin INSTANCE = new FireSpin();

    public FireSpin() {
        super("Fire Spin", 35, Type.FIRE, Category.SPECIAL, 85, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Traps the target for 4-5 turns, dealing 1/8 of its max HP per turn.
    }
}
