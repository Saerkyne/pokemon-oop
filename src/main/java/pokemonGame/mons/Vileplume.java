package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Vileplume extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Vileplume(String name) {
        super(
            "Vileplume",
            45,
            "Grass",
            "Poison",
            5,
            75,
            80,
            85,
            110,
            90,
            50
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 3, 0, 0}; // Vileplume yields 3 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
