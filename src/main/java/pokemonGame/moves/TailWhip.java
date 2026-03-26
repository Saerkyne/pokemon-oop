package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class TailWhip extends Move {
    public static final TailWhip INSTANCE = new TailWhip();

    public TailWhip() {
        super("Tail Whip", 0, Type.NORMAL, Category.STATUS, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's Defense by 1 stage.
    }
}
