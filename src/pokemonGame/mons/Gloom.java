package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Gloom extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Gloom(String name) {
        super("Gloom", 44, "Grass", "Poison",
            5, 60, 65, 70,
            85, 75, 40);

        this.setName(name);

        int[] evYield = {0, 0, 0, 2, 0, 0}; // Gloom yields 2 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
