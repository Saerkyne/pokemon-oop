package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Mew extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Mew(String name) {
        super("Mew", 151, "Psychic", null,
            5, 100, 100, 100,
            100, 100, 100);

        this.setName(name);

        int[] evYield = {3, 0, 0, 0, 0, 0}; // Mew yields 3 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
