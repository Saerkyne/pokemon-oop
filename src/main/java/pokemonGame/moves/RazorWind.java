package pokemonGame.moves;
import pokemonGame.Move;

public class RazorWind extends Move {
    public static final RazorWind INSTANCE = new RazorWind();

    public RazorWind() {
        super("Razor Wind", 80, "Normal", "Special", 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Charges on turn 1, attacks on turn 2. High critical-hit ratio.
    }
}
