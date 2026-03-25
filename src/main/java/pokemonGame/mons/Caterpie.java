package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Caterpie extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(StringShot.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
    }

    public Caterpie(String nickname) {
        super(
            "Caterpie",
            10,
            "Bug",
            null,
            5,
            45,
            30,
            35,
            20,
            20,
            45
        );

        this.setNickname(nickname);

        int[] evYield = {1, 0, 0, 0, 0, 0}; // Caterpie yields 1 EV point in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
