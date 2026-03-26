package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Hypnosis extends Move {
    public static final Hypnosis INSTANCE = new Hypnosis();

    public Hypnosis() {
        super("Hypnosis", 0, Type.PSYCHIC, Category.STATUS, 60, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep.
    }
}
