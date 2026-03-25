package pokemonGame.moves;
import pokemonGame.Move;

public class Tackle extends Move {
    public static final Tackle INSTANCE = new Tackle();

    public Tackle() {
        super("Tackle", 40, "Normal", "Physical", 100, 35);
        // == Special Effect ==
        // No additional effect.
    }

}
