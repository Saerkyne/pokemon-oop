package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Minimize extends Move {
    public static final Minimize INSTANCE = new Minimize();

    public Minimize() {
        super("Minimize", 0, Type.NORMAL, Category.STATUS, 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's evasion by 2 stages.
        // Certain moves (Stomp, Body Slam, etc.) deal double damage
        // to a minimized Pokemon and bypass accuracy checks.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
