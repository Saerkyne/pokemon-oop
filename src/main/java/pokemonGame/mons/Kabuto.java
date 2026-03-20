package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Kabuto extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(new Harden(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Scratch(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Absorb(), LearnsetEntry.Source.LEVEL, 34));
        LEARNSET.add(new LearnsetEntry(new Slash(), LearnsetEntry.Source.LEVEL, 39));
        LEARNSET.add(new LearnsetEntry(new Leer(), LearnsetEntry.Source.LEVEL, 44));
        LEARNSET.add(new LearnsetEntry(new HydroPump(), LearnsetEntry.Source.LEVEL, 49));

        // HM moves
        LEARNSET.add(new LearnsetEntry(new Surf(), LearnsetEntry.Source.HM, 3));

        // TM moves
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new BubbleBeam(), LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Kabuto(String name) {
        super(
            "Kabuto",
            140,
            "Rock",
            "Water",
            5,
            30,
            80,
            90,
            55,
            45,
            55
        );

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Kabuto yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
