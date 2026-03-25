package pokemonGame.moves;
import pokemonGame.Move;


public class Ember extends Move {
    public static final Ember INSTANCE = new Ember();

    public Ember() {
        super("Ember", 40, "Fire", "Special", 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to burn the target.
    }   

}
