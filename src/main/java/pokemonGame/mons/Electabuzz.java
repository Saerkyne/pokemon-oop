package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Electabuzz extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Leer.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(QuickAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(ThunderShock.INSTANCE, LearnsetEntry.Source.LEVEL, 34));
        LEARNSET.add(new LearnsetEntry(Screech.INSTANCE, LearnsetEntry.Source.LEVEL, 37));
        LEARNSET.add(new LearnsetEntry(ThunderPunch.INSTANCE, LearnsetEntry.Source.LEVEL, 42));
        LEARNSET.add(new LearnsetEntry(LightScreen.INSTANCE, LearnsetEntry.Source.LEVEL, 49));
        LEARNSET.add(new LearnsetEntry(Thunder.INSTANCE, LearnsetEntry.Source.LEVEL, 54));

        // HM moves
        LEARNSET.add(new LearnsetEntry(Strength.INSTANCE, LearnsetEntry.Source.HM, 4));
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
        LEARNSET.add(new LearnsetEntry(Thunderbolt.INSTANCE, LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(Thunder.INSTANCE, LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(Teleport.INSTANCE, LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Metronome.INSTANCE, LearnsetEntry.Source.TM, 35));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(ThunderWave.INSTANCE, LearnsetEntry.Source.TM, 45));
        LEARNSET.add(new LearnsetEntry(Psywave.INSTANCE, LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Electabuzz(String nickname) {
        super(
            "Electabuzz",
            125,
            "Electric",
            null,
            5,
            65,
            83,
            57,
            95,
            85,
            105
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Electabuzz yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
