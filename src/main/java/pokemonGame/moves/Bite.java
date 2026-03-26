package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Bite extends Move {
    public static final Bite INSTANCE = new Bite();

    public Bite() {
        super("Bite", 60, Type.DARK,
        Category.PHYSICAL, 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
    }

}
