package pokemonGame.moves;
import pokemonGame.Move;

public class SolarBeam extends Move {
    public SolarBeam() {
        super("Solar Beam", 120, "Grass", "Special", 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Charges on turn 1, attacks on turn 2.
        // No charge needed in harsh sunlight.
        // Deals half damage in rain, sandstorm, snow, and hail.
    }
}
