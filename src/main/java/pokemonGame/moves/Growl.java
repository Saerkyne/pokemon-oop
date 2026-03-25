package pokemonGame.moves;
import pokemonGame.Move;

public class Growl extends Move {
    public static final Growl INSTANCE = new Growl();

    public Growl() {
        super("Growl", 0, "Normal", "Status", 100, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's Attack by 1 stage.
    }
}
