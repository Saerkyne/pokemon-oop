package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Staryu extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.LEVEL, 17));
        LEARNSET.add(new LearnsetEntry(new Harden(), LearnsetEntry.Source.LEVEL, 22));
        LEARNSET.add(new LearnsetEntry(new Recover(), LearnsetEntry.Source.LEVEL, 27));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.LEVEL, 32));
        LEARNSET.add(new LearnsetEntry(new Minimize(), LearnsetEntry.Source.LEVEL, 37));
        LEARNSET.add(new LearnsetEntry(new LightScreen(), LearnsetEntry.Source.LEVEL, 42));
        LEARNSET.add(new LearnsetEntry(new HydroPump(), LearnsetEntry.Source.LEVEL, 47));

        LEARNSET.add(new LearnsetEntry(new Surf(), LearnsetEntry.Source.HM, 3));
        LEARNSET.add(new LearnsetEntry(new Flash(), LearnsetEntry.Source.HM, 5));

        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new BubbleBeam(), LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Thunderbolt(), LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(new Thunder(), LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(new Psychic(), LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(new Teleport(), LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new ThunderWave(), LearnsetEntry.Source.TM, 45));
        LEARNSET.add(new LearnsetEntry(new Psywave(), LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(new TriAttack(), LearnsetEntry.Source.TM, 49));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Staryu(String name) {
        super(
            "Staryu",
            120,
            "Water",
            null,
            5,
            30,
            45,
            55,
            70,
            55,
            85
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Staryu yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
