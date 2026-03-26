package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Dig extends Move {
    public static final Dig INSTANCE = new Dig();

    public Dig() {
        super("Dig", 80, Type.GROUND, Category.PHYSICAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User digs underground on turn 1, attacks on turn 2.
        // While underground, user is semi-invulnerable (can still be
        // hit by Earthquake, Magnitude, and Fissure).
    }
}
