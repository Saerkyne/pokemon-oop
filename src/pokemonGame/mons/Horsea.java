package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Horsea extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Horsea(String name) {
        super("Horsea", 116, "Water", null,
            5, 30, 40, 70,
            70, 25, 60);

        this.setName(name);

        int[] evYield = {0, 0, 0, 1, 0, 0}; // Horsea yields 1 EV point in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
