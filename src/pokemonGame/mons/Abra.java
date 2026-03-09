package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Abra extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Abra(String name) {
        super(
            "Abra",
            63,
            "Psychic",
            null,
            5,
            25,
            20,
            15,
            105,
            55,
            90
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 1, 0, 0}; // Abra yields 1 EV point in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
