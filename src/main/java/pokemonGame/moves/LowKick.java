package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class LowKick extends Move {
    public static final LowKick INSTANCE = new LowKick();

    public LowKick() {
        super("Low Kick", 0, Type.FIGHTING, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Damage varies based on the target's weight (heavier = more damage).
        // Power: 0 is a placeholder — PokemonDB lists no base power (weight-based).
    }
}
