package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Magmar extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Ember.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Leer.INSTANCE, LearnsetEntry.Source.LEVEL, 36));
        LEARNSET.add(new LearnsetEntry(ConfuseRay.INSTANCE, LearnsetEntry.Source.LEVEL, 39));
        LEARNSET.add(new LearnsetEntry(FirePunch.INSTANCE, LearnsetEntry.Source.LEVEL, 43));
        LEARNSET.add(new LearnsetEntry(Smokescreen.INSTANCE, LearnsetEntry.Source.LEVEL, 48));
        LEARNSET.add(new LearnsetEntry(Smog.INSTANCE, LearnsetEntry.Source.LEVEL, 52));
        LEARNSET.add(new LearnsetEntry(Flamethrower.INSTANCE, LearnsetEntry.Source.LEVEL, 55));

        // HM moves
        LEARNSET.add(new LearnsetEntry(Strength.INSTANCE, LearnsetEntry.Source.HM, 4));

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
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Metronome.INSTANCE, LearnsetEntry.Source.TM, 35));
        LEARNSET.add(new LearnsetEntry(FireBlast.INSTANCE, LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Psywave.INSTANCE, LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Magmar(String nickname) {
        super(
            "Magmar",
            126,
            "Fire",
            null,
            5,
            65,
            95,
            57,
            100,
            85,
            93
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 2, 0, 0}; // Magmar yields 2 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
