package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Screech extends Move {
    public static final Screech INSTANCE = new Screech();

    public Screech() {
        super("Screech", 0, Type.NORMAL, Category.STATUS, 85, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's Defense by 2 stages. Sound-based move.
    }
}
