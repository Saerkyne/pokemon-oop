package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Clefairy extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Clefairy(String name) {
        super(
            "Clefairy",
            35,
            "Fairy",
            null,
            5,
            70,
            45,
            48,
            60,
            65,
            35
        );

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Clefairy yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
