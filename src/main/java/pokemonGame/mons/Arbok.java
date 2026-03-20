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
            new LearnsetEntry(new Leer(), LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(new PoisonSting(), LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(new Wrap(), LearnsetEntry.Source.LEVEL, 1)
        );
        LEARNSET.add(
            new LearnsetEntry(new PoisonSting(), LearnsetEntry.Source.LEVEL, 10)
        );
        LEARNSET.add(
            new LearnsetEntry(new Bite(), LearnsetEntry.Source.LEVEL, 17)
        );
        LEARNSET.add(
            new LearnsetEntry(new Glare(), LearnsetEntry.Source.LEVEL, 27)
        );
        LEARNSET.add(
            new LearnsetEntry(new Screech(), LearnsetEntry.Source.LEVEL, 36)
        );
        LEARNSET.add(
            new LearnsetEntry(new Acid(), LearnsetEntry.Source.LEVEL, 47)
        );
        // HM Moves
        LEARNSET.add(
            new LearnsetEntry(new Strength(), LearnsetEntry.Source.HM, 4)
        );
        // TM Moves
        LEARNSET.add(
            new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6)
        );
        LEARNSET.add(
            new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8)
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
            new LearnsetEntry(new Earthquake(), LearnsetEntry.Source.TM, 26)
        );
        LEARNSET.add(
            new LearnsetEntry(new Fissure(), LearnsetEntry.Source.TM, 27)
        );
        LEARNSET.add(new LearnsetEntry(new Dig(), LearnsetEntry.Source.TM, 28));
        LEARNSET.add(
            new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31)
        );
        LEARNSET.add(
            new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32)
        );
        LEARNSET.add(
            new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34)
        );
        LEARNSET.add(
            new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40)
        );
        LEARNSET.add(
            new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44)
        );
        LEARNSET.add(
            new LearnsetEntry(new RockSlide(), LearnsetEntry.Source.TM, 48)
        );
        LEARNSET.add(
            new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50)
        );
    }

    public Arbok(String name) {
        super("Arbok", 24, "Poison", null, 5, 60, 95, 69, 65, 79, 80);
        this.setName(name);

        int[] evYield = { 0, 2, 0, 0, 0, 0 }; // Arbok yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
