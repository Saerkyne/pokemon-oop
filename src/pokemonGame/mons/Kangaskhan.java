package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Kangaskhan extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Kangaskhan(String name) {
        super(
            "Kangaskhan",
            115,
            "Normal",
            null,
            5,
            105,
            95,
            80,
            40,
            80,
            90
        );

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Kangaskhan yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
