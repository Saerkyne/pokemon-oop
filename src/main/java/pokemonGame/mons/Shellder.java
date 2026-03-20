package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Shellder extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Withdraw(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Supersonic(), LearnsetEntry.Source.LEVEL, 18));
        LEARNSET.add(new LearnsetEntry(new Clamp(), LearnsetEntry.Source.LEVEL, 23));
        LEARNSET.add(new LearnsetEntry(new AuroraBeam(), LearnsetEntry.Source.LEVEL, 30));
        LEARNSET.add(new LearnsetEntry(new Leer(), LearnsetEntry.Source.LEVEL, 39));
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.LEVEL, 50));

        LEARNSET.add(new LearnsetEntry(new Surf(), LearnsetEntry.Source.HM, 3));

        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new BubbleBeam(), LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Teleport(), LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new SelfDestruct(), LearnsetEntry.Source.TM, 36));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Explosion(), LearnsetEntry.Source.TM, 47));
        LEARNSET.add(new LearnsetEntry(new TriAttack(), LearnsetEntry.Source.TM, 49));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Shellder(String name) {
        super(
            "Shellder",
            90,
            "Water",
            null,
            5,
            30,
            65,
            100,
            45,
            25,
            40
        );

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Shellder yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
