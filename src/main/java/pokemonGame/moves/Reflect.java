package pokemonGame.moves;
import pokemonGame.Move;

public class Reflect extends Move {
    public Reflect() {
        super("Reflect", 0, "Psychic", "Status", 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Reduces physical damage taken by the user's team by 50% for 5 turns.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
