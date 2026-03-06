package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Kabutops extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Kabutops(String name) {
        super("Kabutops", 141, "Rock", "Water",
            5, 60, 115, 105,
            65, 70, 80);

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Kabutops yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
