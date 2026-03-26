package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class DreamEater extends Move {
    public static final DreamEater INSTANCE = new DreamEater();

    public DreamEater() {
        super("Dream Eater", 100, Type.PSYCHIC, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Only works on sleeping targets. Fails if the target is awake.
        // User recovers 50% of the damage dealt.
    }
}
