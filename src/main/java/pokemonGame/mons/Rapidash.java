package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Rapidash extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Rapidash(String name) {
        super(
            "Rapidash",
            78,
            "Fire",
            null,
            5,
            65,
            100,
            70,
            80,
            80,
            105
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Rapidash yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
