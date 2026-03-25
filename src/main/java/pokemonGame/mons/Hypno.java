package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Hypno extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Confusion.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Disable.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Hypnosis.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Pound.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Disable.INSTANCE, LearnsetEntry.Source.LEVEL, 12));
        LEARNSET.add(new LearnsetEntry(Confusion.INSTANCE, LearnsetEntry.Source.LEVEL, 17));
        LEARNSET.add(new LearnsetEntry(Headbutt.INSTANCE, LearnsetEntry.Source.LEVEL, 24));
        LEARNSET.add(new LearnsetEntry(PoisonGas.INSTANCE, LearnsetEntry.Source.LEVEL, 33));
        LEARNSET.add(new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.LEVEL, 37));
        LEARNSET.add(new LearnsetEntry(Meditate.INSTANCE, LearnsetEntry.Source.LEVEL, 43));

        // HM moves
        LEARNSET.add(new LearnsetEntry(Flash.INSTANCE, LearnsetEntry.Source.HM, 5));

        // TM moves
        LEARNSET.add(new LearnsetEntry(MegaPunch.INSTANCE, LearnsetEntry.Source.TM, 1));
        LEARNSET.add(new LearnsetEntry(MegaKick.INSTANCE, LearnsetEntry.Source.TM, 5));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Submission.INSTANCE, LearnsetEntry.Source.TM, 17));
        LEARNSET.add(new LearnsetEntry(Counter.INSTANCE, LearnsetEntry.Source.TM, 18));
        LEARNSET.add(new LearnsetEntry(SeismicToss.INSTANCE, LearnsetEntry.Source.TM, 19));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(Teleport.INSTANCE, LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Metronome.INSTANCE, LearnsetEntry.Source.TM, 35));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(DreamEater.INSTANCE, LearnsetEntry.Source.TM, 42));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(ThunderWave.INSTANCE, LearnsetEntry.Source.TM, 45));
        LEARNSET.add(new LearnsetEntry(Psywave.INSTANCE, LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(TriAttack.INSTANCE, LearnsetEntry.Source.TM, 49));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Hypno(String nickname) {
        super(
            "Hypno",
            97,
            "Psychic",
            null,
            5,
            85,
            73,
            70,
            73,
            115,
            67
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 2, 0}; // Hypno yields 2 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
