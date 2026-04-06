package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Hypnosis extends Move {
    public static final Hypnosis INSTANCE = new Hypnosis();

    public Hypnosis() {
        super("Hypnosis", 0, Type.PSYCHIC, Category.STATUS, 60, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep.
    }
}
