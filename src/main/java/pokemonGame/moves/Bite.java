package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Bite extends Move {
    public static final Bite INSTANCE = new Bite();

    public Bite() {
        super("Bite", 60, Type.DARK,
        Category.PHYSICAL, 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
    }

}
