package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Zapdos extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Zapdos(String name) {
        super("Zapdos", 145, "Electric", "Flying",
            5, 90, 90, 85,
            125, 90, 100);

        this.setName(name);

        int[] evYield = {0, 0, 0, 3, 0, 0}; // Zapdos yields 3 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
