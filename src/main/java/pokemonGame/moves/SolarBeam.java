package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class SolarBeam extends Move {
    public static final SolarBeam INSTANCE = new SolarBeam();

    public SolarBeam() {
        super("Solar Beam", 120, Type.GRASS, Category.SPECIAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Charges on turn 1, attacks on turn 2.
        // No charge needed in harsh sunlight.
        // Deals half damage in rain, sandstorm, snow, and hail.
    }
}
