package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Weedle extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(PoisonSting.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(StringShot.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
    }

    public Weedle(String nickname) {
        super(
            "Weedle",
            13,
            "Bug",
            "Poison",
            5,
            40,
            35,
            30,
            20,
            20,
            50
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Weedle yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
