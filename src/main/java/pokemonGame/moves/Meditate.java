package pokemonGame.moves;
import pokemonGame.Move;

public class Meditate extends Move {
    public Meditate() {
        super("Meditate", 0, "Psychic", "Status", 0, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Attack by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
