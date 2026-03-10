package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class MrMime extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public MrMime(String name) {
        super(
            "Mr. Mime",
            122,
            "Psychic",
            "Fairy",
            5,
            40,
            45,
            65,
            100,
            120,
            90
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 2, 0}; // Mr. Mime yields 2 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
