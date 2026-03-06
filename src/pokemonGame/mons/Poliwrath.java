package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Poliwrath extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Poliwrath(String name) {
        super("Poliwrath", 62, "Water", "Fighting",
            5, 90, 95, 95,
            70, 90, 70);

        this.setName(name);

        int[] evYield = {0, 0, 3, 0, 0, 0}; // Poliwrath yields 3 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
