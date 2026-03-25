package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Weezing extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(Sludge.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Smog.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Sludge.INSTANCE, LearnsetEntry.Source.LEVEL, 32));
        LEARNSET.add(new LearnsetEntry(Smokescreen.INSTANCE, LearnsetEntry.Source.LEVEL, 39));
        LEARNSET.add(new LearnsetEntry(SelfDestruct.INSTANCE, LearnsetEntry.Source.LEVEL, 43));
        LEARNSET.add(new LearnsetEntry(Haze.INSTANCE, LearnsetEntry.Source.LEVEL, 49));
        LEARNSET.add(new LearnsetEntry(Explosion.INSTANCE, LearnsetEntry.Source.LEVEL, 53));

        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Thunderbolt.INSTANCE, LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(Thunder.INSTANCE, LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(SelfDestruct.INSTANCE, LearnsetEntry.Source.TM, 36));
        LEARNSET.add(new LearnsetEntry(FireBlast.INSTANCE, LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Explosion.INSTANCE, LearnsetEntry.Source.TM, 47));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Weezing(String nickname) {
        super(
            "Weezing",
            110,
            "Poison",
            null,
            5,
            65,
            90,
            120,
            85,
            70,
            60
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 2, 0, 0, 0}; // Weezing yields 2 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
