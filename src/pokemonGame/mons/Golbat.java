package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Golbat extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Golbat(String name) {
        super("Golbat", 42, "Poison", "Flying",
            5, 75, 80, 70,
            65, 75, 90);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Golbat yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
