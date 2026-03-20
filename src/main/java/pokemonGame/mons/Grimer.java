package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Grimer extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(new Disable(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Pound(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new PoisonGas(), LearnsetEntry.Source.LEVEL, 30));
        LEARNSET.add(new LearnsetEntry(new Minimize(), LearnsetEntry.Source.LEVEL, 33));
        LEARNSET.add(new LearnsetEntry(new Sludge(), LearnsetEntry.Source.LEVEL, 37));
        LEARNSET.add(new LearnsetEntry(new Harden(), LearnsetEntry.Source.LEVEL, 42));
        LEARNSET.add(new LearnsetEntry(new Screech(), LearnsetEntry.Source.LEVEL, 48));
        LEARNSET.add(new LearnsetEntry(new AcidArmor(), LearnsetEntry.Source.LEVEL, 55));

        // TM moves
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new MegaDrain(), LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(new Thunderbolt(), LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(new Thunder(), LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new SelfDestruct(), LearnsetEntry.Source.TM, 36));
        LEARNSET.add(new LearnsetEntry(new FireBlast(), LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Explosion(), LearnsetEntry.Source.TM, 47));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Grimer(String name) {
        super(
            "Grimer",
            88,
            "Poison",
            null,
            5,
            80,
            80,
            50,
            40,
            50,
            25
        );

        this.setName(name);

        int[] evYield = {1, 0, 0, 0, 0, 0}; // Grimer yields 1 EV point in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
