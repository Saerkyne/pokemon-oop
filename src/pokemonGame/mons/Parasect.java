package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Parasect extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Parasect(String name) {
        super("Parasect", 47, "Bug", "Grass",
            5, 60, 95, 80,
            60, 80, 30);

        this.setName(name);

        int[] evYield = {0, 2, 1, 0, 0, 0}; // Parasect yields 2 EV points in Attack and 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
