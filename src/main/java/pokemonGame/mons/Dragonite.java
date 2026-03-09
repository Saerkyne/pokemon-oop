package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Dragonite extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Dragonite(String name) {
        super(
            "Dragonite",
            149,
            "Dragon",
            "Flying",
            5,
            91,
            134,
            95,
            100,
            100,
            80
        );

        this.setName(name);

        int[] evYield = {0, 3, 0, 0, 0, 0}; // Dragonite yields 3 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
