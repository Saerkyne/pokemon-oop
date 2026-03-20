package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Ditto extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(new Transform(), LearnsetEntry.Source.LEVEL, 1));
    }

    public Ditto(String name) {
        super(
            "Ditto",
            132,
            "Normal",
            null,
            5,
            48,
            48,
            48,
            48,
            48,
            48
        );

        this.setName(name);

        int[] evYield = {1, 0, 0, 0, 0, 0}; // Ditto yields 1 EV point in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
