package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Swift extends Move {
    public static final Swift INSTANCE = new Swift();

    public Swift() {
        super("Swift", 60, Type.NORMAL, Category.SPECIAL, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Bypasses accuracy checks; always hits the target
        // (except during semi-invulnerable turns of Fly, Dig, etc.).
        // Accuracy: 0 is a placeholder — PokemonDB lists "—" (never misses).
    }

}
