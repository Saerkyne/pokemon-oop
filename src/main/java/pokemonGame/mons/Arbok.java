package pokemonGame.mons;

import java.util.List;
import pokemonGame.LearnsetEntry;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;

public class Arbok extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET =
        new java.util.ArrayList<>();

    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(Leer.INSTANCE, LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(PoisonSting.INSTANCE, LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(Wrap.INSTANCE, LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(PoisonSting.INSTANCE, LearnsetEntry.Source.LEVEL, 10)
        );
        LEARNSET.add(
            new LearnsetEntry(Bite.INSTANCE, LearnsetEntry.Source.LEVEL, 17)
        );
        LEARNSET.add(
            new LearnsetEntry(Glare.INSTANCE, LearnsetEntry.Source.LEVEL, 27)
        );
        LEARNSET.add(
            new LearnsetEntry(Screech.INSTANCE, LearnsetEntry.Source.LEVEL, 36)
        );
        LEARNSET.add(
            new LearnsetEntry(Acid.INSTANCE, LearnsetEntry.Source.LEVEL, 47)
        );
        // HM Moves
        LEARNSET.add(
            new LearnsetEntry(Strength.INSTANCE, LearnsetEntry.Source.HM, 4)
        );
        // TM Moves
        LEARNSET.add(
            new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6)
        );
        LEARNSET.add(
            new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8)
        );
        LEARNSET.add(
            new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9)
        );
        LEARNSET.add(
            new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10)
        );
        LEARNSET.add(
            new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15)
        );
        LEARNSET.add(
            new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20)
        );
        LEARNSET.add(
            new LearnsetEntry(MegaDrain.INSTANCE, LearnsetEntry.Source.TM, 21)
        );
        LEARNSET.add(
            new LearnsetEntry(Earthquake.INSTANCE, LearnsetEntry.Source.TM, 26)
        );
        LEARNSET.add(
            new LearnsetEntry(Fissure.INSTANCE, LearnsetEntry.Source.TM, 27)
        );
        LEARNSET.add(new LearnsetEntry(Dig.INSTANCE, LearnsetEntry.Source.TM, 28));
        LEARNSET.add(
            new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31)
        );
        LEARNSET.add(
            new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32)
        );
        LEARNSET.add(
            new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34)
        );
        LEARNSET.add(
            new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40)
        );
        LEARNSET.add(
            new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44)
        );
        LEARNSET.add(
            new LearnsetEntry(RockSlide.INSTANCE, LearnsetEntry.Source.TM, 48)
        );
        LEARNSET.add(
            new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50)
        );
    }

    public Arbok(String nickname) {
        super(
            "Arbok",
            24,
            "Poison",
            null,
            5,
            60,
            95,
            69,
            65,
            79,
            80
        );

        this.setNickname(nickname);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Arbok yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
