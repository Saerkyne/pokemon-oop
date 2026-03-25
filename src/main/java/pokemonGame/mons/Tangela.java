package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Tangela extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(Bind.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Constrict.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Absorb.INSTANCE, LearnsetEntry.Source.LEVEL, 29));
        LEARNSET.add(new LearnsetEntry(PoisonPowder.INSTANCE, LearnsetEntry.Source.LEVEL, 32));
        LEARNSET.add(new LearnsetEntry(StunSpore.INSTANCE, LearnsetEntry.Source.LEVEL, 36));
        LEARNSET.add(new LearnsetEntry(SleepPowder.INSTANCE, LearnsetEntry.Source.LEVEL, 39));
        LEARNSET.add(new LearnsetEntry(Slam.INSTANCE, LearnsetEntry.Source.LEVEL, 45));
        LEARNSET.add(new LearnsetEntry(Growth.INSTANCE, LearnsetEntry.Source.LEVEL, 49));

        LEARNSET.add(new LearnsetEntry(Cut.INSTANCE, LearnsetEntry.Source.HM, 1));

        LEARNSET.add(new LearnsetEntry(SwordsDance.INSTANCE, LearnsetEntry.Source.TM, 3));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(MegaDrain.INSTANCE, LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(SolarBeam.INSTANCE, LearnsetEntry.Source.TM, 22));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Tangela(String nickname) {
        super(
            "Tangela",
            114,
            "Grass",
            null,
            5,
            65,
            55,
            115,
            100,
            40,
            60
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Tangela yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
