package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Diglett extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Diglett(String name) {
        super(
            "Diglett",
            50,
            "Ground",
            null,
            5,
            10,
            55,
            25,
            35,
            45,
            95
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Diglett yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
