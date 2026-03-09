package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Golem extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Golem(String name) {
        super(
            "Golem",
            76,
            "Rock",
            "Ground",
            5,
            80,
            120,
            130,
            55,
            65,
            45
        );

        this.setName(name);

        int[] evYield = {0, 0, 3, 0, 0, 0}; // Golem yields 3 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
