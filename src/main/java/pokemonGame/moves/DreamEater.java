package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class DreamEater extends Move {
    public static final DreamEater INSTANCE = new DreamEater();

    public DreamEater() {
        super("Dream Eater", 100, Type.PSYCHIC, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Only works on sleeping targets. Fails if the target is awake.
        // User recovers 50% of the damage dealt.
    }
}
