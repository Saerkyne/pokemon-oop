package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Psybeam extends Move {
    public static final Psybeam INSTANCE = new Psybeam();

    public Psybeam() {
        super("Psybeam", 65, Type.PSYCHIC, Category.SPECIAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to confuse the target.
    }
}
