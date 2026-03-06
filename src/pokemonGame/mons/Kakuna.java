package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Kakuna extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Kakuna(String name) {
        super("Kakuna", 14, "Bug", "Poison",
            5, 45, 25, 50,
            25, 25, 35);

        this.setName(name);

        int[] evYield = {0, 0, 2, 0, 0, 0}; // Kakuna yields 2 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
