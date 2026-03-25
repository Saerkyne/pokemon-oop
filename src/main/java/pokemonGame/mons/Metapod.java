package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Metapod extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Harden.INSTANCE, LearnsetEntry.Source.LEVEL, 1));

        // Pre-evolution moves
        LEARNSET.add(new LearnsetEntry(StringShot.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 0));
    }

    public Metapod(String nickname) {
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

        this.setNickname(nickname);

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
