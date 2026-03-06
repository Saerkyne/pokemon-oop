package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Dodrio extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Dodrio(String name) {
        super("Dodrio", 85, "Normal", "Flying",
            5, 60, 110, 70,
            60, 60, 110);

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Dodrio yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
