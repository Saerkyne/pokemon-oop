package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Abra extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(new LearnsetEntry(new Teleport(), LearnsetEntry.Source.LEVEL, 1));
        // HM Moves
        LEARNSET.add(new LearnsetEntry(new Flash(), LearnsetEntry.Source.HM, 5));
        // TM Moves
        LEARNSET.add(new LearnsetEntry(new MegaPunch(), LearnsetEntry.Source.TM, 1));
        LEARNSET.add(new LearnsetEntry(new MegaKick(), LearnsetEntry.Source.TM, 5));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new Submission(), LearnsetEntry.Source.TM, 17));
        LEARNSET.add(new LearnsetEntry(new Counter(), LearnsetEntry.Source.TM, 18));
        LEARNSET.add(new LearnsetEntry(new SeismicToss(), LearnsetEntry.Source.TM, 19));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Psychic(), LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(new Teleport(), LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Metronome(), LearnsetEntry.Source.TM, 35));
        LEARNSET.add(new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new ThunderWave(), LearnsetEntry.Source.TM, 45));
        LEARNSET.add(new LearnsetEntry(new Psywave(), LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(new TriAttack(), LearnsetEntry.Source.TM, 49));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Abra(String name) {
        super(
            "Abra",
            63,
            "Psychic",
            null,
            5,
            25,
            20,
            15,
            105,
            55,
            90
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 1, 0, 0}; // Abra yields 1 EV point in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
        
        // Set current HP to max HP at Pokemon Generation
        this.setCurrentHP(getMaxHP());
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
