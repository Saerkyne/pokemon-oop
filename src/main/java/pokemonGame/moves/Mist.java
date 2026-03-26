package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Mist extends Move {
    public static final Mist INSTANCE = new Mist();

    public Mist() {
        super("Mist", 0, Type.ICE, Category.STATUS, 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Protects the user's team from stat reductions caused by
        // the opponent for 5 turns.
    }

}
