package pokemonGame.moves;
import pokemonGame.Move;

public class Bonemerang extends Move {
    public static final Bonemerang INSTANCE = new Bonemerang();

    public Bonemerang() {
        super("Bonemerang", 50, "Ground",
        "Physical", 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Hits the target twice in one turn.
    }

}
