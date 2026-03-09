package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Clefable extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Clefable(String name) {
        super(
            "Clefable",
            36,
            "Fairy",
            null,
            5,
            95,
            70,
            73,
            95,
            90,
            60
        );

        this.setName(name);

        int[] evYield = {3, 0, 0, 0, 0, 0}; // Clefable yields 3 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
