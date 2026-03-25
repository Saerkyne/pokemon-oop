package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Kingler extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Bubble.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Leer.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(ViseGrip.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(ViseGrip.INSTANCE, LearnsetEntry.Source.LEVEL, 20));
        LEARNSET.add(new LearnsetEntry(Guillotine.INSTANCE, LearnsetEntry.Source.LEVEL, 25));
        LEARNSET.add(new LearnsetEntry(Stomp.INSTANCE, LearnsetEntry.Source.LEVEL, 34));
        LEARNSET.add(new LearnsetEntry(Crabhammer.INSTANCE, LearnsetEntry.Source.LEVEL, 42));
        LEARNSET.add(new LearnsetEntry(Harden.INSTANCE, LearnsetEntry.Source.LEVEL, 49));

        // HM moves
        LEARNSET.add(new LearnsetEntry(Cut.INSTANCE, LearnsetEntry.Source.HM, 1));
        LEARNSET.add(new LearnsetEntry(Surf.INSTANCE, LearnsetEntry.Source.HM, 3));
        LEARNSET.add(new LearnsetEntry(Strength.INSTANCE, LearnsetEntry.Source.HM, 4));

        // TM moves
        LEARNSET.add(new LearnsetEntry(SwordsDance.INSTANCE, LearnsetEntry.Source.TM, 3));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(BubbleBeam.INSTANCE, LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(WaterGun.INSTANCE, LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(IceBeam.INSTANCE, LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(Blizzard.INSTANCE, LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Kingler(String nickname) {
        super(
            "Kingler",
            99,
            "Water",
            null,
            5,
            55,
            130,
            115,
            50,
            50,
            75
        );

        this.setNickname(nickname);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Kingler yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
