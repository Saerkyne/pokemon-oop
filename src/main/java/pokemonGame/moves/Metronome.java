package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

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
