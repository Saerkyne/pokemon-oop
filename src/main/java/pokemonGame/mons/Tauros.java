package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Tauros extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Tauros(String name) {
        super(
            "Tauros",
            128,
            "Normal",
            null,
            5,
            75,
            100,
            95,
            40,
            70,
            110
        );

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 1}; // Tauros yields 1 EV point in Attack and 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
