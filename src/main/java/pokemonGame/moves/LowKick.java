package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class LowKick extends Move {
    public static final LowKick INSTANCE = new LowKick();

    public LowKick() {
        super("Low Kick", 0, Type.FIGHTING, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Damage varies based on the target's weight (heavier = more damage).
        // Power: 0 is a placeholder — PokemonDB lists no base power (weight-based).
    }
}
