package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;


public class Ember extends Move {
    public static final Ember INSTANCE = new Ember();

    public Ember() {
        super("Ember", 40, Type.FIRE, Category.SPECIAL, 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }   

}
