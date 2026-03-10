package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Magneton extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Magneton(String name) {
        super(
            "Magneton",
            82,
            "Electric",
            "Steel",
            5,
            50,
            60,
            95,
            120,
            70,
            70
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 2, 0, 0}; // Magneton yields 2 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
