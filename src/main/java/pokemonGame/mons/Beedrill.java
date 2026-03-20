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
            new LearnsetEntry(new FuryAttack(), LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(new FuryAttack(), LearnsetEntry.Source.LEVEL, 12)
        );
        LEARNSET.add(
            new LearnsetEntry(new FocusEnergy(), LearnsetEntry.Source.LEVEL, 16)
        );
        LEARNSET.add(
            new LearnsetEntry(new Twineedle(), LearnsetEntry.Source.LEVEL, 20)
        );
        LEARNSET.add(
            new LearnsetEntry(new Rage(), LearnsetEntry.Source.LEVEL, 25)
        );
        LEARNSET.add(
            new LearnsetEntry(new PinMissile(), LearnsetEntry.Source.LEVEL, 30)
        );
        LEARNSET.add(
            new LearnsetEntry(new Agility(), LearnsetEntry.Source.LEVEL, 35)
        );

        // HM moves
        LEARNSET.add(new LearnsetEntry(new Cut(), LearnsetEntry.Source.HM, 1));

        // Pre-evolution moves
        LEARNSET.add(
            new LearnsetEntry(
                new Harden(),
                LearnsetEntry.Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                new PoisonSting(),
                LearnsetEntry.Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                new StringShot(),
                LearnsetEntry.Source.PRE_EVOLUTION,
                1
            )
        );

        // TM moves
        LEARNSET.add(
            new LearnsetEntry(new SwordsDance(), LearnsetEntry.Source.TM, 3)
        );
        LEARNSET.add(
            new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6)
        );
        LEARNSET.add(
            new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9)
        );
        LEARNSET.add(
            new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10)
        );
        LEARNSET.add(
            new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15)
        );
        LEARNSET.add(
            new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20)
        );
        LEARNSET.add(
            new LearnsetEntry(new MegaDrain(), LearnsetEntry.Source.TM, 21)
        );
        LEARNSET.add(
            new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31)
        );
        LEARNSET.add(
            new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32)
        );
        LEARNSET.add(
            new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33)
        );
        LEARNSET.add(
            new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34)
        );
        LEARNSET.add(
            new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39)
        );
        LEARNSET.add(
            new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40)
        );
        LEARNSET.add(
            new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44)
        );
        LEARNSET.add(
            new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50)
        );
    }

    public Beedrill(String name) {
        super("Beedrill", 15, "Bug", "Poison", 5, 65, 90, 40, 45, 80, 75);
        this.setName(name);

        int[] evYield = { 0, 2, 0, 0, 1, 0 }; // Beedrill yields 2 EV points in Attack and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
