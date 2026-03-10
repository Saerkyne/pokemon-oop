package pokemonGame.moves;
import pokemonGame.Move;

public class Withdraw extends Move {
    public Withdraw() {
        super("Withdraw", 0, "Water", "Status", 0, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Defense by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
