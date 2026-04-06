package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Toxic extends Move {
    public static final Toxic INSTANCE = new Toxic();

    public Toxic() {
        super("Toxic", 0, Type.POISON, Category.STATUS, 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Badly poisons the target. Damage increases each turn:
        // 1/16, 2/16, 3/16, etc. of max HP.
    }
}
