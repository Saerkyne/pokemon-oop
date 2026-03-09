package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Alakazam extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Alakazam(String name) {
        super(
            "Alakazam",
            65,
            "Psychic",
            null,
            5,
            55,
            50,
            45,
            135,
            95,
            120
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 3, 0, 0}; // Alakazam yields 3 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
