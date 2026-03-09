package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Scyther extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Scyther(String name) {
        super("Scyther", 123, "Bug", "Flying",
            5, 70, 110, 80,
            55, 80, 105);

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Scyther yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
