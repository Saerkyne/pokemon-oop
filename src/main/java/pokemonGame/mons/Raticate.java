package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Raticate extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Raticate(String name) {
        super(
            "Raticate",
            20,
            "Normal",
            null,
            5,
            55,
            81,
            60,
            50,
            70,
            97
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Raticate yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
