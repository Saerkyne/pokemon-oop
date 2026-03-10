package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Weepinbell extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Weepinbell(String name) {
        super(
            "Weepinbell",
            70,
            "Grass",
            "Poison",
            5,
            65,
            90,
            50,
            85,
            45,
            55
        );

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Weepinbell yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
