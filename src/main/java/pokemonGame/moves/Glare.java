package pokemonGame.moves;
import pokemonGame.Move;

public class Glare extends Move {
    public static final Glare INSTANCE = new Glare();

    public Glare() {
        super("Glare", 0, "Normal", "Status", 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Paralyzes the target.
    }
}
