package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Agility extends Move {
    public static final Agility INSTANCE = new Agility();

    public Agility() {
        super("Agility", 0, Type.PSYCHIC,
        Category.STATUS, 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Speed by 2 stages.
    }

}
