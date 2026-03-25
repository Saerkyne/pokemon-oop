package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Rhyhorn extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(HornAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Stomp.INSTANCE, LearnsetEntry.Source.LEVEL, 30));
        LEARNSET.add(new LearnsetEntry(TailWhip.INSTANCE, LearnsetEntry.Source.LEVEL, 35));
        LEARNSET.add(new LearnsetEntry(FuryAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 40));
        LEARNSET.add(new LearnsetEntry(HornDrill.INSTANCE, LearnsetEntry.Source.LEVEL, 45));
        LEARNSET.add(new LearnsetEntry(Leer.INSTANCE, LearnsetEntry.Source.LEVEL, 50));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.LEVEL, 55));

        LEARNSET.add(new LearnsetEntry(Strength.INSTANCE, LearnsetEntry.Source.HM, 4));

        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(HornDrill.INSTANCE, LearnsetEntry.Source.TM, 7));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Thunderbolt.INSTANCE, LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(Thunder.INSTANCE, LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(Earthquake.INSTANCE, LearnsetEntry.Source.TM, 26));
        LEARNSET.add(new LearnsetEntry(Fissure.INSTANCE, LearnsetEntry.Source.TM, 27));
        LEARNSET.add(new LearnsetEntry(Dig.INSTANCE, LearnsetEntry.Source.TM, 28));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(FireBlast.INSTANCE, LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(RockSlide.INSTANCE, LearnsetEntry.Source.TM, 48));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Rhyhorn(String nickname) {
        super(
            "Rhyhorn",
            111,
            "Ground",
            "Rock",
            5,
            80,
            85,
            95,
            30,
            30,
            25
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Rhyhorn yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
