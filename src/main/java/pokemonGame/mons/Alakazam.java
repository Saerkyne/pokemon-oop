package pokemonGame.mons;

import java.util.List;
import pokemonGame.LearnsetEntry;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;

public class Alakazam extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET =
        new java.util.ArrayList<>();

    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(Confusion.INSTANCE, LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(Disable.INSTANCE, LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(Teleport.INSTANCE, LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(Confusion.INSTANCE, LearnsetEntry.Source.LEVEL, 16)
        );
        LEARNSET.add(
            new LearnsetEntry(Disable.INSTANCE, LearnsetEntry.Source.LEVEL, 20)
        );
        LEARNSET.add(
            new LearnsetEntry(Psybeam.INSTANCE, LearnsetEntry.Source.LEVEL, 27)
        );
        LEARNSET.add(
            new LearnsetEntry(Recover.INSTANCE, LearnsetEntry.Source.LEVEL, 31)
        );
        LEARNSET.add(
            new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.LEVEL, 38)
        );
        LEARNSET.add(
            new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.LEVEL, 42)
        );
        // HM Moves
        LEARNSET.add(
            new LearnsetEntry(Flash.INSTANCE, LearnsetEntry.Source.HM, 5)
        );
        // TM Moves
        LEARNSET.add(
            new LearnsetEntry(MegaPunch.INSTANCE, LearnsetEntry.Source.TM, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(MegaKick.INSTANCE, LearnsetEntry.Source.TM, 5)
        );
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
            new LearnsetEntry(Submission.INSTANCE, LearnsetEntry.Source.TM, 17)
        );
        LEARNSET.add(
            new LearnsetEntry(Counter.INSTANCE, LearnsetEntry.Source.TM, 18)
        );
        LEARNSET.add(
            new LearnsetEntry(SeismicToss.INSTANCE, LearnsetEntry.Source.TM, 19)
        );
        LEARNSET.add(
            new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20)
        );
        LEARNSET.add(new LearnsetEntry(Dig.INSTANCE, LearnsetEntry.Source.TM, 28));
        LEARNSET.add(
            new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.TM, 29)
        );
        LEARNSET.add(
            new LearnsetEntry(Teleport.INSTANCE, LearnsetEntry.Source.TM, 30)
        );
        LEARNSET.add(
            new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31)
        );
        LEARNSET.add(
            new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32)
        );
        LEARNSET.add(
            new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33)
        );
        LEARNSET.add(
            new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34)
        );
        LEARNSET.add(
            new LearnsetEntry(Metronome.INSTANCE, LearnsetEntry.Source.TM, 35)
        );
        LEARNSET.add(
            new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40)
        );
        LEARNSET.add(
            new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44)
        );
        LEARNSET.add(
            new LearnsetEntry(ThunderWave.INSTANCE, LearnsetEntry.Source.TM, 45)
        );
        LEARNSET.add(
            new LearnsetEntry(Psywave.INSTANCE, LearnsetEntry.Source.TM, 46)
        );
        LEARNSET.add(
            new LearnsetEntry(TriAttack.INSTANCE, LearnsetEntry.Source.TM, 49)
        );
        LEARNSET.add(
            new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50)
        );
    }

    public Alakazam(String nickname) {
        super(
            "Alakazam",
            65,
            "Psychic",
            null,
            5,
            55,
            50,
            45,
            135,
            95,
            120
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 3, 0, 0}; // Alakazam yields 3 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
