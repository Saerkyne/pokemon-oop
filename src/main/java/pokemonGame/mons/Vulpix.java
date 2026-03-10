package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Vulpix extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Vulpix(String name) {
        super(
            "Vulpix",
            37,
            "Fire",
            null,
            5,
            38,
            41,
            40,
            50,
            65,
            65
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Vulpix yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
