package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Wartortle extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Growl.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Strength.INSTANCE, LearnsetEntry.Source.LEVEL, 8));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.LEVEL, 23));
    }

    public Wartortle(String nickname) {
        super(
            "Wartortle",
            8,
            "Water",
            null,
            5,
            59,
            63,
            80,
            65,
            80,
            58
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 1, 0, 1, 0}; // Wartortle yields 1 EV point in Defense and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}

