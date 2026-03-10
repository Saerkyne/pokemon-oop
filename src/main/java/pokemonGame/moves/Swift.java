package pokemonGame.moves;
import pokemonGame.Move;

public class Swift extends Move {
    public Swift() {
        super("Swift", 60, "Normal", "Special", 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Bypasses accuracy checks; always hits the target
        // (except during semi-invulnerable turns of Fly, Dig, etc.).
        // Accuracy: 0 is a placeholder — PokemonDB lists "—" (never misses).
    }

}
