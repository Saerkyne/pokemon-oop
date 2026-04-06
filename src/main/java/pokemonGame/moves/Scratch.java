package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;


public class Scratch extends Move{
    public static final Scratch INSTANCE = new Scratch();

    public Scratch() {
        super("Scratch", 40, Type.NORMAL, Category.PHYSICAL, 100, 35);
        // == Special Effect ==
        // No additional effect.
    }
}
