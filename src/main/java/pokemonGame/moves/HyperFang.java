package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class HyperFang extends Move {
    public static final HyperFang INSTANCE = new HyperFang();

    public HyperFang() {
        super("Hyper Fang", 80, Type.NORMAL, Category.PHYSICAL, 90, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to make the target flinch.
    }
}
