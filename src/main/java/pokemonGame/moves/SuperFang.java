package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class SuperFang extends Move {
    public static final SuperFang INSTANCE = new SuperFang();

    public SuperFang() {
        super("Super Fang", 0, Type.NORMAL, Category.PHYSICAL, 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Deals damage equal to half the target's current HP.
        // Always deals at least 1 HP of damage.
        // Power: 0 is a placeholder — PokemonDB lists no base power (% HP damage).
    }
}
