package pokemonGame.moves;
import pokemonGame.Move;

public class TriAttack extends Move {
    public TriAttack() {
        super("Tri Attack", 80, "Normal", "Special", 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 20% chance to inflict burn, freeze, or paralysis
        // (6.67% chance of each).
    }
}
