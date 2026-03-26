package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Headbutt extends Move {
    public static final Headbutt INSTANCE = new Headbutt();

    public Headbutt() {
        super("Headbutt", 70, Type.NORMAL, Category.PHYSICAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
    }
}
