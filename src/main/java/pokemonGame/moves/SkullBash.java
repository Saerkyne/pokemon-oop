package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class SkullBash extends Move {
    public static final SkullBash INSTANCE = new SkullBash();

    public SkullBash() {
        super("Skull Bash", 130, Type.NORMAL, Category.PHYSICAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Charges on turn 1 (raises user's Defense by 1 stage),
        // then attacks on turn 2.
    }
}
