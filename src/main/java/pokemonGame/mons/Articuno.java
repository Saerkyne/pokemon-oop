package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Articuno extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(IceBeam.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Peck.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Blizzard.INSTANCE, LearnsetEntry.Source.LEVEL, 51));
        LEARNSET.add(new LearnsetEntry(Agility.INSTANCE, LearnsetEntry.Source.LEVEL, 55));
        LEARNSET.add(new LearnsetEntry(Mist.INSTANCE, LearnsetEntry.Source.LEVEL, 60));

        // HM moves
        LEARNSET.add(new LearnsetEntry(Fly.INSTANCE, LearnsetEntry.Source.HM, 2));

        // TM Moves
        LEARNSET.add(new LearnsetEntry(RazorWind.INSTANCE, LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(Whirlwind.INSTANCE, LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(BubbleBeam.INSTANCE, LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(WaterGun.INSTANCE, LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(IceBeam.INSTANCE, LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(Blizzard.INSTANCE, LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));  
        LEARNSET.add(new LearnsetEntry(SkyAttack.INSTANCE, LearnsetEntry.Source.TM, 43));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));  
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));    
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
