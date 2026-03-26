package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Fly extends Move {
    public static final Fly INSTANCE = new Fly();

    public Fly() {
        super("Fly", 90, Type.FLYING, Category.PHYSICAL, 95, 15);
        // == Special Effect (Not Yet Implemented) ==
        // User flies up on turn 1, attacks on turn 2.
        // While airborne, user is semi-invulnerable (can still be hit
        // by Gust, Thunder, Twister, Sky Uppercut, and Hurricane).
    }
}
