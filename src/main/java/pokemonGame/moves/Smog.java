package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Smog extends Move {
    public static final Smog INSTANCE = new Smog();

    public Smog() {
        super("Smog", 30, Type.POISON, Category.SPECIAL, 70, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 40% chance to poison the target.
    }
}
