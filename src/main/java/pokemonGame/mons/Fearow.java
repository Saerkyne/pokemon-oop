package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Fearow extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Fearow(String name) {
        super(
            "Fearow",
            22,
            "Normal",
            "Flying",
            5,
            65,
            90,
            65,
            61,
            61,
            100
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Fearow yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
