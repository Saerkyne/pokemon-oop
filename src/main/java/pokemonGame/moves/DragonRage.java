package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class DragonRage extends Move {
    public static final DragonRage INSTANCE = new DragonRage();

    public DragonRage() {
        super("Dragon Rage", 0, Type.DRAGON, Category.SPECIAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Always deals exactly 40 HP damage regardless of stats or type.
        // Power: 0 is a placeholder — PokemonDB lists no base power (fixed damage).
    }
}
