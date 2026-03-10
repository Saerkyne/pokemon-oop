package pokemonGame.moves;
import pokemonGame.Move;

public class SuperFang extends Move {
    public SuperFang() {
        super("Super Fang", 0, "Normal", "Physical", 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Deals damage equal to half the target's current HP.
        // Always deals at least 1 HP of damage.
        // Power: 0 is a placeholder — PokemonDB lists no base power (% HP damage).
    }
}
