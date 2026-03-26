package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class HornDrill extends Move {
    public static final HornDrill INSTANCE = new HornDrill();

    public HornDrill() {
        super("Horn Drill", 0, Type.NORMAL, Category.PHYSICAL, 30, 5);
        // == Special Effect (Not Yet Implemented) ==
        // OHKO move. Instantly knocks out the target if it hits.
        // Accuracy = 30 + (user's level - target's level).
        // Fails automatically if the target is a higher level.
        // Power: 0 is a placeholder — PokemonDB lists no base power (OHKO).
    }
}
