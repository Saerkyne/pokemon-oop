package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Dewgong extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Dewgong(String name) {
        super(
            "Dewgong",
            87,
            "Water",
            "Ice",
            5,
            90,
            70,
            80,
            70,
            95,
            70
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 2, 0}; // Dewgong yields 2 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
