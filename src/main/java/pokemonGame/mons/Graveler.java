package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Graveler extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(DefenseCurl.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(DefenseCurl.INSTANCE, LearnsetEntry.Source.LEVEL, 11));
        LEARNSET.add(new LearnsetEntry(RockThrow.INSTANCE, LearnsetEntry.Source.LEVEL, 16));
        LEARNSET.add(new LearnsetEntry(SelfDestruct.INSTANCE, LearnsetEntry.Source.LEVEL, 21));
        LEARNSET.add(new LearnsetEntry(Harden.INSTANCE, LearnsetEntry.Source.LEVEL, 29));
        LEARNSET.add(new LearnsetEntry(Earthquake.INSTANCE, LearnsetEntry.Source.LEVEL, 36));
        LEARNSET.add(new LearnsetEntry(Explosion.INSTANCE, LearnsetEntry.Source.LEVEL, 43));

        // HM moves
        LEARNSET.add(new LearnsetEntry(Strength.INSTANCE, LearnsetEntry.Source.HM, 4));

        // TM moves
        LEARNSET.add(new LearnsetEntry(MegaPunch.INSTANCE, LearnsetEntry.Source.TM, 1));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(Submission.INSTANCE, LearnsetEntry.Source.TM, 17));
        LEARNSET.add(new LearnsetEntry(Counter.INSTANCE, LearnsetEntry.Source.TM, 18));
        LEARNSET.add(new LearnsetEntry(SeismicToss.INSTANCE, LearnsetEntry.Source.TM, 19));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Earthquake.INSTANCE, LearnsetEntry.Source.TM, 26));
        LEARNSET.add(new LearnsetEntry(Fissure.INSTANCE, LearnsetEntry.Source.TM, 27));
        LEARNSET.add(new LearnsetEntry(Dig.INSTANCE, LearnsetEntry.Source.TM, 28));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Metronome.INSTANCE, LearnsetEntry.Source.TM, 35));
        LEARNSET.add(new LearnsetEntry(SelfDestruct.INSTANCE, LearnsetEntry.Source.TM, 36));
        LEARNSET.add(new LearnsetEntry(FireBlast.INSTANCE, LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Explosion.INSTANCE, LearnsetEntry.Source.TM, 47));
        LEARNSET.add(new LearnsetEntry(RockSlide.INSTANCE, LearnsetEntry.Source.TM, 48));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Graveler(String nickname) {
        super(
            "Graveler",
            75,
            "Rock",
            "Ground",
            5,
            55,
            95,
            115,
            45,
            45,
            35
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 2, 0, 0, 0}; // Graveler yields 2 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
