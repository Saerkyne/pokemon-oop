package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Bellsprout extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Bellsprout(String name) {
        super(
            "Bellsprout",
            69,
            "Grass",
            "Poison",
            5,
            50,
            75,
            35,
            70,
            30,
            40
        );

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Bellsprout yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
