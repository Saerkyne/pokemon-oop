package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Slowbro extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Slowbro(String name) {
        super("Slowbro", 80, "Water", "Psychic",
            5, 95, 75, 110,
            100, 80, 30);

        this.setName(name);

        int[] evYield = {0, 0, 2, 0, 0, 0}; // Slowbro yields 2 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
