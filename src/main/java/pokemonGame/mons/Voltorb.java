package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Voltorb extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Voltorb(String name) {
        super("Voltorb", 100, "Electric", null,
            5, 40, 30, 50,
            55, 55, 100);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Voltorb yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
