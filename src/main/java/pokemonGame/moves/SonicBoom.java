package pokemonGame.moves;
import pokemonGame.Move;

public class SonicBoom extends Move {
    public static final SonicBoom INSTANCE = new SonicBoom();

    public SonicBoom() {
        super("Sonic Boom", 0, "Normal", "Special", 90, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Always deals exactly 20 HP damage regardless of stats or type.
        // Power: 0 is a placeholder — PokemonDB lists no base power (fixed damage).
    }
}
