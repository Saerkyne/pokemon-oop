package pokemonGame.moves;
import pokemonGame.Move;

public class LowKick extends Move {
    public LowKick() {
        super("Low Kick", 0, "Fighting", "Physical", 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Damage varies based on the target's weight (heavier = more damage).
        // Power: 0 is a placeholder — PokemonDB lists no base power (weight-based).
    }
}
