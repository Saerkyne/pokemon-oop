package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Golduck extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Golduck(String name) {
        super(
            "Golduck",
            55,
            "Water",
            null,
            5,
            80,
            82,
            78,
            95,
            80,
            85
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 2, 0, 0}; // Golduck yields 2 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
