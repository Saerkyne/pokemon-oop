package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Aerodactyl extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
         // Level Up Moves
        LEARNSET.add(new LearnsetEntry(new Agility(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new WingAttack(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Supersonic(), LearnsetEntry.Source.LEVEL, 33));
        LEARNSET.add(new LearnsetEntry(new Bite(), LearnsetEntry.Source.LEVEL, 38));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.LEVEL, 45));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.LEVEL, 54));
        // HM Moves
        LEARNSET.add(new LearnsetEntry(new Fly(), LearnsetEntry.Source.HM, 2));
        // TM Moves
        LEARNSET.add(new LearnsetEntry(new RazorWind(), LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(new Whirlwind(), LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new DragonRage(), LearnsetEntry.Source.TM, 23));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new FireBlast(), LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(new SkyAttack(), LearnsetEntry.Source.TM, 43));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Aerodactyl(String nickname) {
        super(
            "Aerodactyl",
            142,
            "Rock",
            "Flying",
            5,
            80,
            105,
            65,
            60,
            75,
            130
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Aerodactyl yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
