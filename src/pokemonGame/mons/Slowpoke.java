package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Slowpoke extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Slowpoke(String name) {
        super("Slowpoke", 79, "Water", "Psychic",
            5, 90, 65, 65,
            40, 40, 15);

        this.setName(name);

        int[] evYield = {1, 0, 0, 0, 0, 0}; // Slowpoke yields 1 EV point in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
