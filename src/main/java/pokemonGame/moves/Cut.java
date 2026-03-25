package pokemonGame.moves;
import pokemonGame.Move;

public class Cut extends Move {
    public static final Cut INSTANCE = new Cut();

    public Cut() {
        super("Cut", 50, "Normal",
        "Physical", 95, 30);
        // == Special Effect ==
        // No additional effect.
    }
}
