package pokemonGame.moves;
import pokemonGame.Move;

public class Psywave extends Move {
    public Psywave() {
        super("Psywave", 0, "Psychic", "Special", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Deals random damage between 50% and 150% of the user's level.
        // Power: 0 is a placeholder — PokemonDB lists no base power (variable damage).
    }
}
