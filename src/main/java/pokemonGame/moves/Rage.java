package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Rage extends Move {
    public static final Rage INSTANCE = new Rage();

    public Rage() {
        super("Rage", 20, Type.NORMAL, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // User's Attack rises by 1 stage each time it is hit
        // while using this move.
    }
}
