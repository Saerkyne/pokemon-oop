package pokemonGame.moves;
import pokemonGame.Move;

public class KarateChop extends Move {
    public static final KarateChop INSTANCE = new KarateChop();

    public KarateChop() {
        super("Karate Chop", 50, "Fighting", "Physical", 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
