package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class KarateChop extends Move {
    public static final KarateChop INSTANCE = new KarateChop();

    public KarateChop() {
        super("Karate Chop", 50, Type.FIGHTING, Category.PHYSICAL, 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
