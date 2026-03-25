package pokemonGame.mons;

import java.util.List;
import pokemonGame.LearnsetEntry;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;

public class Beedrill extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET =
        new java.util.ArrayList<>();

    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(FuryAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(FuryAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 12)
        );
        LEARNSET.add(
            new LearnsetEntry(FocusEnergy.INSTANCE, LearnsetEntry.Source.LEVEL, 16)
        );
        LEARNSET.add(
            new LearnsetEntry(Twineedle.INSTANCE, LearnsetEntry.Source.LEVEL, 20)
        );
        LEARNSET.add(
            new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.LEVEL, 25)
        );
        LEARNSET.add(
            new LearnsetEntry(PinMissile.INSTANCE, LearnsetEntry.Source.LEVEL, 30)
        );
        LEARNSET.add(
            new LearnsetEntry(Agility.INSTANCE, LearnsetEntry.Source.LEVEL, 35)
        );

        // HM moves
        LEARNSET.add(new LearnsetEntry(Cut.INSTANCE, LearnsetEntry.Source.HM, 1));

        // Pre-evolution moves
        LEARNSET.add(
            new LearnsetEntry(
                Harden.INSTANCE,
                LearnsetEntry.Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PoisonSting.INSTANCE,
                LearnsetEntry.Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StringShot.INSTANCE,
                LearnsetEntry.Source.PRE_EVOLUTION,
                1
            )
        );

        // TM moves
        LEARNSET.add(
            new LearnsetEntry(SwordsDance.INSTANCE, LearnsetEntry.Source.TM, 3)
        );
        LEARNSET.add(
            new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6)
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
            new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39)
        );
        LEARNSET.add(
            new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40)
        );
        LEARNSET.add(
            new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44)
        );
        LEARNSET.add(
            new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50)
        );
    }

    public Beedrill(String nickname) {
        super(
            "Beedrill",
            15,
            "Bug",
            "Poison",
            5,
            65,
            90,
            40,
            45,
            80,
            75
        );

        this.setNickname(nickname);

        int[] evYield = {0, 2, 0, 0, 1, 0}; // Beedrill yields 2 EV points in Attack and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
