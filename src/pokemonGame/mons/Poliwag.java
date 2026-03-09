package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Poliwag extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Poliwag(String name) {
        super("Poliwag", 60, "Water", null,
            5, 40, 50, 40,
            40, 40, 90);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Poliwag yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
