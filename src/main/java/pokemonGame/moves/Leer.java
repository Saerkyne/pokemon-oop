package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Leer extends Move {
    public static final Leer INSTANCE = new Leer();

    public Leer() {
        super("Leer", 0, Type.NORMAL, Category.STATUS, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's Defense by 1 stage.
    }
}
