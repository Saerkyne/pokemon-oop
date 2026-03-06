package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Shellder extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Shellder(String name) {
        super("Shellder", 90, "Water", null,
            5, 30, 65, 100,
            45, 25, 40);

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Shellder yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
