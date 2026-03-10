package pokemonGame.moves;
import pokemonGame.Move;

public class Bide extends Move {
    public Bide() {
        super("Bide", 0, "Normal",
        "Physical", 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User endures attacks for 2 turns, then strikes back with
        // double the damage received.
        // Accuracy: 0 is a placeholder — PokemonDB lists "—" (always hits).
    }

}
