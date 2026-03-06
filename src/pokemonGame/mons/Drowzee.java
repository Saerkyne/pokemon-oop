package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Drowzee extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Drowzee(String name) {
        super("Drowzee", 96, "Psychic", null,
            5, 60, 48, 45,
            43, 90, 42);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 1, 0}; // Drowzee yields 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
