package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Moltres extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Moltres(String name) {
        super(
            "Moltres",
            146,
            "Fire",
            "Flying",
            5,
            90,
            100,
            90,
            125,
            85,
            90
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 3, 0, 0}; // Moltres yields 3 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
