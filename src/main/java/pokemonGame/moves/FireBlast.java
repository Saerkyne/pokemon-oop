package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class FireBlast extends Move {
    public static final FireBlast INSTANCE = new FireBlast();

    public FireBlast() {
        super("Fire Blast", 110, Type.FIRE, Category.SPECIAL, 85, 5);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }
}
