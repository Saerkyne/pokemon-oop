package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Paras extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Paras(String name) {
        super(
            "Paras",
            46,
            "Bug",
            "Grass",
            5,
            35,
            70,
            55,
            45,
            55,
            25
        );

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Paras yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
