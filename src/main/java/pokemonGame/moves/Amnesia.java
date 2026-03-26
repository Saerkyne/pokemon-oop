package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Amnesia extends Move {
    public static final Amnesia INSTANCE = new Amnesia();

    public Amnesia() {
        super("Amnesia", 0, Type.PSYCHIC,
        Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Sp. Def by 2 stages.
    }
}
