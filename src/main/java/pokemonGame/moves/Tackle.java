package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Tackle extends Move {
    public static final Tackle INSTANCE = new Tackle();

    public Tackle() {
        super("Tackle", 40, Type.NORMAL, Category.PHYSICAL, 100, 35);
        // == Special Effect ==
        // No additional effect.
    }

}
