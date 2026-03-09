package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Beedrill extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Beedrill(String name) {
        super(
            "Beedrill",
            15,
            "Bug",
            "Poison",
            5,
            65,
            90,
            40,
            45,
            80,
            75
        );

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 1, 0}; // Beedrill yields 2 EV points in Attack and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
