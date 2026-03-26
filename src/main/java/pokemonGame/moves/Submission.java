package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Submission extends Move {
    public static final Submission INSTANCE = new Submission();

    public Submission() {
        super("Submission", 80, Type.FIGHTING, Category.PHYSICAL, 80, 20);
        // == Special Effect (Not Yet Implemented) ==
        // User takes 25% of the damage dealt as recoil.
    }
}
