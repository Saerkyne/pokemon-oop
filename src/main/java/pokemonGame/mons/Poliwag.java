package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Poliwag extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(Bubble.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Hypnosis.INSTANCE, LearnsetEntry.Source.LEVEL, 16));
        LEARNSET.add(new LearnsetEntry(WaterGun.INSTANCE, LearnsetEntry.Source.LEVEL, 19));
        LEARNSET.add(new LearnsetEntry(DoubleSlap.INSTANCE, LearnsetEntry.Source.LEVEL, 25));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.LEVEL, 31));
        LEARNSET.add(new LearnsetEntry(Amnesia.INSTANCE, LearnsetEntry.Source.LEVEL, 38));
        LEARNSET.add(new LearnsetEntry(HydroPump.INSTANCE, LearnsetEntry.Source.LEVEL, 45));

        LEARNSET.add(new LearnsetEntry(Surf.INSTANCE, LearnsetEntry.Source.HM, 3));

        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(BubbleBeam.INSTANCE, LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(WaterGun.INSTANCE, LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(IceBeam.INSTANCE, LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(Blizzard.INSTANCE, LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Psywave.INSTANCE, LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Poliwag(String nickname) {
        super(
            "Poliwag",
            60,
            "Water",
            null,
            5,
            40,
            50,
            40,
            40,
            40,
            90
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Poliwag yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
