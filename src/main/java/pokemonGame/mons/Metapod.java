package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Metapod extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(new Harden(), LearnsetEntry.Source.LEVEL, 1));

        // Pre-evolution moves
        LEARNSET.add(new LearnsetEntry(new StringShot(), LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.PRE_EVOLUTION, 0));
    }

    public Metapod(String name) {
        super(
            "Metapod",
            11,
            "Bug",
            null,
            5,
            50,
            20,
            55,
            25,
            25,
            30
        );

        this.setName(name);

        int[] evYield = {0, 0, 2, 0, 0, 0}; // Metapod yields 2 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
