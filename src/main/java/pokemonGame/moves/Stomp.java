package pokemonGame.moves;
import pokemonGame.Move;

public class Stomp extends Move {
    public Stomp() {
        super("Stomp", 65, "Normal", "Physical", 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
        // Deals double damage to a target that has used Minimize.
    }
}
