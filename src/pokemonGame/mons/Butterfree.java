package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Butterfree extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Butterfree(String name) {
        super("Butterfree", 12, "Bug", "Flying",
            5, 60, 45, 50,
            90, 80, 70);

        this.setName(name);

        int[] evYield = {0, 0, 0, 2, 1, 0}; // Butterfree yields 2 EV points in Special Attack and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
