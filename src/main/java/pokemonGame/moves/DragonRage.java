package pokemonGame.moves;
import pokemonGame.Move;

public class DragonRage extends Move {
    public DragonRage() {
        super("Dragon Rage", 0, "Dragon", "Special", 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Always deals exactly 40 HP damage regardless of stats or type.
        // Power: 0 is a placeholder — PokemonDB lists no base power (fixed damage).
    }
}
