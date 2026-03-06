package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Exeggcute extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Exeggcute(String name) {
        super("Exeggcute", 102, "Grass", "Psychic",
            5, 60, 40, 80,
            60, 45, 40);

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Exeggcute yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
