package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Poliwhirl extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Poliwhirl(String name) {
        super(
            "Poliwhirl",
            61,
            "Water",
            null,
            5,
            65,
            65,
            65,
            50,
            50,
            90
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Poliwhirl yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
