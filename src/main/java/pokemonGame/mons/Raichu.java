package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Raichu extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Raichu(String name) {
        super(
            "Raichu",
            26,
            "Electric",
            null,
            5,
            60,
            90,
            55,
            90,
            80,
            110
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 3}; // Raichu yields 3 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
