package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Nidoqueen extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Nidoqueen(String name) {
        super("Nidoqueen", 31, "Poison", "Ground",
            5, 90, 92, 87,
            75, 85, 76);

        this.setName(name);

        int[] evYield = {3, 0, 0, 0, 0, 0}; // Nidoqueen yields 3 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
