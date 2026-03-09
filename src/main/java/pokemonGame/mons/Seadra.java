package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Seadra extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Seadra(String name) {
        super("Seadra", 117, "Water", null,
            5, 55, 65, 95,
            95, 45, 85);

        this.setName(name);

        int[] evYield = {0, 0, 1, 1, 0, 0}; // Seadra yields 1 EV point in Defense and 1 EV point in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
