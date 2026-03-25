package pokemonGame.moves;
import pokemonGame.Move;

public class Slam extends Move {
    public static final Slam INSTANCE = new Slam();

    public Slam() {
        super("Slam", 80, "Normal", "Physical", 75, 20);
        // == Special Effect ==
        // No additional effect.
    }
}
