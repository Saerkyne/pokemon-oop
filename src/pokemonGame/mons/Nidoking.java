package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Nidoking extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Nidoking(String name) {
        super("Nidoking", 34, "Poison", "Ground",
            5, 81, 102, 77,
            85, 75, 85);

        this.setName(name);

        int[] evYield = {0, 3, 0, 0, 0, 0}; // Nidoking yields 3 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
