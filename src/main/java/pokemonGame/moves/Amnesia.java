package pokemonGame.moves;
import pokemonGame.Move;

public class Amnesia extends Move {
    public static final Amnesia INSTANCE = new Amnesia();

    public Amnesia() {
        super("Amnesia", 0, "Psychic",
        "Status", 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Sp. Def by 2 stages.
    }
}
