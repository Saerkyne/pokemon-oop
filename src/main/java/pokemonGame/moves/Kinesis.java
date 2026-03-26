package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Kinesis extends Move {
    public static final Kinesis INSTANCE = new Kinesis();

    public Kinesis() {
        super("Kinesis", 0, Type.PSYCHIC, Category.STATUS, 80, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's accuracy by 1 stage.
    }
}
