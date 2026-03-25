package pokemonGame.moves;
import pokemonGame.Move;

public class Crabhammer extends Move {
    public static final Crabhammer INSTANCE = new Crabhammer();

    public Crabhammer() {
        super("Crabhammer", 100, "Water",
        "Physical", 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // High critical-hit ratio.
    }
}
