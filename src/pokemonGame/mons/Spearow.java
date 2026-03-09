package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Spearow extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Spearow(String name) {
        super("Spearow", 21, "Normal", "Flying",
            5, 40, 60, 30,
            31, 31, 70);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Spearow yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
