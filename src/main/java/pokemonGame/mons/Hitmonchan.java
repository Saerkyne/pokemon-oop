package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Hitmonchan extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Agility.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(CometPunch.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(FirePunch.INSTANCE, LearnsetEntry.Source.LEVEL, 33));
        LEARNSET.add(new LearnsetEntry(IcePunch.INSTANCE, LearnsetEntry.Source.LEVEL, 38));
        LEARNSET.add(new LearnsetEntry(ThunderPunch.INSTANCE, LearnsetEntry.Source.LEVEL, 43));
        LEARNSET.add(new LearnsetEntry(MegaPunch.INSTANCE, LearnsetEntry.Source.LEVEL, 48));
        LEARNSET.add(new LearnsetEntry(Counter.INSTANCE, LearnsetEntry.Source.LEVEL, 53));

        // HM moves
        LEARNSET.add(new LearnsetEntry(Strength.INSTANCE, LearnsetEntry.Source.HM, 4));

        // TM moves
        LEARNSET.add(new LearnsetEntry(MegaPunch.INSTANCE, LearnsetEntry.Source.TM, 1));
        LEARNSET.add(new LearnsetEntry(MegaKick.INSTANCE, LearnsetEntry.Source.TM, 5));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(Submission.INSTANCE, LearnsetEntry.Source.TM, 17));
        LEARNSET.add(new LearnsetEntry(Counter.INSTANCE, LearnsetEntry.Source.TM, 18));
        LEARNSET.add(new LearnsetEntry(SeismicToss.INSTANCE, LearnsetEntry.Source.TM, 19));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Metronome.INSTANCE, LearnsetEntry.Source.TM, 35));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Hitmonchan(String nickname) {
        super(
            "Hitmonchan",
            107,
            "Fighting",
            null,
            5,
            50,
            105,
            79,
            35,
            110,
            76
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 2, 0}; // Hitmonchan yields 2 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
