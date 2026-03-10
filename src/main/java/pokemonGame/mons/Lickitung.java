package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Lickitung extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Lickitung(String name) {
        super(
            "Lickitung",
            108,
            "Normal",
            null,
            5,
            90,
            55,
            75,
            60,
            75,
            30
        );

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Lickitung yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
