package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Hitmonchan extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Hitmonchan(String name) {
        super(
            "Hitmonchan",
            107,
            "Fighting",
            null,
            5,
            50,
            105,
            79,
            35,
            110,
            76
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 2, 0}; // Hitmonchan yields 2 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
