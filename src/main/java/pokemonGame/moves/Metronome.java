package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Metronome extends Move {
    public static final Metronome INSTANCE = new Metronome();

    public Metronome() {
        super("Metronome", 0, Type.NORMAL, Category.STATUS, 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Randomly selects and uses any other move
        // (excluding certain restricted moves like Metronome itself).
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (depends on called move).
    }
}
