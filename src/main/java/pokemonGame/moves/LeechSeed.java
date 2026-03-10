package pokemonGame.moves;
import pokemonGame.Move;

public class LeechSeed extends Move {
    public LeechSeed() {
        super("Leech Seed", 0, "Grass", "Status", 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Seeds the target, draining 1/8 of its max HP each turn
        // and restoring that amount to the user. Does not affect
        // Grass-type Pokémon.
    }
}
