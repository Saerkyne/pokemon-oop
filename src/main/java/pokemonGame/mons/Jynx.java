package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Jynx extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Jynx(String name) {
        super(
            "Jynx",
            124,
            "Ice",
            "Psychic",
            5,
            65,
            50,
            35,
            115,
            95,
            95
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 2, 0, 0}; // Jynx yields 2 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
