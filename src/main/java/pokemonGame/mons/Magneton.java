package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Magneton extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(SonicBoom.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(ThunderShock.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(SonicBoom.INSTANCE, LearnsetEntry.Source.LEVEL, 21));
        LEARNSET.add(new LearnsetEntry(ThunderShock.INSTANCE, LearnsetEntry.Source.LEVEL, 25));
        LEARNSET.add(new LearnsetEntry(Supersonic.INSTANCE, LearnsetEntry.Source.LEVEL, 29));
        LEARNSET.add(new LearnsetEntry(ThunderWave.INSTANCE, LearnsetEntry.Source.LEVEL, 38));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.LEVEL, 46));
        LEARNSET.add(new LearnsetEntry(Screech.INSTANCE, LearnsetEntry.Source.LEVEL, 54));

        // HM moves
        LEARNSET.add(new LearnsetEntry(Flash.INSTANCE, LearnsetEntry.Source.HM, 5));

        // TM moves
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Thunderbolt.INSTANCE, LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(Thunder.INSTANCE, LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(Teleport.INSTANCE, LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(ThunderWave.INSTANCE, LearnsetEntry.Source.TM, 45));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Magneton(String nickname) {
        super(
            "Magneton",
            82,
            "Electric",
            "Steel",
            5,
            50,
            60,
            95,
            120,
            70,
            70
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 2, 0, 0}; // Magneton yields 2 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
