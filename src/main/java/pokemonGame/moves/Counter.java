package pokemonGame.moves;
import pokemonGame.Move;

public class Counter extends Move {
    public Counter() {
        super("Counter", 0, "Fighting",
        "Physical", 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // If the user is hit by a physical attack this turn, it deals
        // double the damage received back to the attacker. Priority -5.
    }
}
