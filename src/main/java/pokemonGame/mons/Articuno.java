package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Articuno extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Peck(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.LEVEL, 51));
        LEARNSET.add(new LearnsetEntry(new Agility(), LearnsetEntry.Source.LEVEL, 55));
        LEARNSET.add(new LearnsetEntry(new Mist(), LearnsetEntry.Source.LEVEL, 60));

        // HM moves
        LEARNSET.add(new LearnsetEntry(new Fly(), LearnsetEntry.Source.HM, 2));

        // TM Moves
        LEARNSET.add(new LearnsetEntry(new RazorWind(), LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(new Whirlwind(), LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new BubbleBeam(), LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39));  
        LEARNSET.add(new LearnsetEntry(new SkyAttack(), LearnsetEntry.Source.TM, 43));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));  
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));    
    }

    public Articuno(String nickname) {
        super(
            "Articuno",
            144,
            "Ice",
            "Flying",
            5,
            90,
            85,
            100,
            95,
            125,
            85
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 3, 0}; // Articuno yields 3 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
