package pokemonGame.moves;
import pokemonGame.Move;

public class Fissure extends Move {
    public Fissure() {
        super("Fissure", 0, "Ground", "Physical", 30, 5);
        // == Special Effect (Not Yet Implemented) ==
        // OHKO move. Instantly knocks out the target if it hits.
        // Accuracy = 30 + (user's level - target's level).
        // Fails automatically if the target is a higher level.
        // Power: 0 is a placeholder — PokemonDB lists no base power (OHKO).
    }
}
