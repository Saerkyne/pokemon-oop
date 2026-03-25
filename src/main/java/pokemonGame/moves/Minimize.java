package pokemonGame.moves;
import pokemonGame.Move;

public class Minimize extends Move {
    public static final Minimize INSTANCE = new Minimize();

    public Minimize() {
        super("Minimize", 0, "Normal", "Status", 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's evasion by 2 stages.
        // Certain moves (Stomp, Body Slam, etc.) deal double damage
        // to a minimized Pokemon and bypass accuracy checks.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
