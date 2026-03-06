package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Ekans extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Ekans(String name) {
        super("Ekans", 23, "Poison", null,
            5, 35, 60, 44,
            40, 54, 55);

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Ekans yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
