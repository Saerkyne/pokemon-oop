package pokemonGame.moves;
import pokemonGame.Move;

public class PayDay extends Move {
    public static final PayDay INSTANCE = new PayDay();

    public PayDay() {
        super("Pay Day", 40, "Normal", "Physical", 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Scatters coins equal to 5x the user's level after battle.
    }
}
