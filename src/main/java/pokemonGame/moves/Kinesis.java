package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Kinesis extends Move {
    public static final Kinesis INSTANCE = new Kinesis();

    public Kinesis() {
        super("Kinesis", 0, Type.PSYCHIC, Category.STATUS, 80, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's accuracy by 1 stage.
    }
}
