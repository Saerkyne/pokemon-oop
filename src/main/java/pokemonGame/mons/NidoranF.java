package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.SpeciesAliases;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class NidoranF extends Pokemon {

    @SpeciesAliases({"nidoranf", "nidoran♀", "nidoran f", "f nidoran", "fnidoran"}) // Handle common variations in naming

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new Growl(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Scratch(), LearnsetEntry.Source.LEVEL, 8));
        LEARNSET.add(new LearnsetEntry(new PoisonSting(), LearnsetEntry.Source.LEVEL, 14));
        LEARNSET.add(new LearnsetEntry(new TailWhip(), LearnsetEntry.Source.LEVEL, 21));
        LEARNSET.add(new LearnsetEntry(new Bite(), LearnsetEntry.Source.LEVEL, 29));
        LEARNSET.add(new LearnsetEntry(new FurySwipes(), LearnsetEntry.Source.LEVEL, 36));
        LEARNSET.add(new LearnsetEntry(new DoubleKick(), LearnsetEntry.Source.LEVEL, 43));

        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Thunderbolt(), LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(new Thunder(), LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public NidoranF(String nickname) {
        super(
            "NidoranF",
            29,
            "Poison",
            null,
            5,
            55,
            47,
            52,
            40,
            40,
            41
        );

        this.setNickname(nickname);

        int[] evYield = {1, 0, 0, 0, 0, 0}; // NidoranF yields 1 EV point in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
