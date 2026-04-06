package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

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
