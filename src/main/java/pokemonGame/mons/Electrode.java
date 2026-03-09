package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Electrode extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Electrode(String name) {
        super(
            "Electrode",
            101,
            "Electric",
            null,
            5,
            60,
            50,
            70,
            80,
            80,
            150
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Electrode yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
