package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Jolteon extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Jolteon(String name) {
        super(
            "Jolteon",
            135,
            "Electric",
            null,
            5,
            65,
            65,
            60,
            110,
            95,
            130
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Jolteon yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
