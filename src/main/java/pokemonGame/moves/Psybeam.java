package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Psybeam extends Move {
    public static final Psybeam INSTANCE = new Psybeam();

    public Psybeam() {
        super("Psybeam", 65, Type.PSYCHIC, Category.SPECIAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to confuse the target.
    }
}
