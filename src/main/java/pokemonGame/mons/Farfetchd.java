package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.SpeciesAliases;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Farfetchd extends Pokemon {

    @SpeciesAliases({"farfetchd", "farfetch'd", "farfetch"}) // Handle common variations in naming

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Peck.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(SandAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Leer.INSTANCE, LearnsetEntry.Source.LEVEL, 7));
        LEARNSET.add(new LearnsetEntry(FuryAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 15));
        LEARNSET.add(new LearnsetEntry(SwordsDance.INSTANCE, LearnsetEntry.Source.LEVEL, 23));
        LEARNSET.add(new LearnsetEntry(Agility.INSTANCE, LearnsetEntry.Source.LEVEL, 31));
        LEARNSET.add(new LearnsetEntry(Slash.INSTANCE, LearnsetEntry.Source.LEVEL, 39));

        // HM moves
        LEARNSET.add(new LearnsetEntry(Cut.INSTANCE, LearnsetEntry.Source.HM, 1));
        LEARNSET.add(new LearnsetEntry(Fly.INSTANCE, LearnsetEntry.Source.HM, 2));

        // TM moves
        LEARNSET.add(new LearnsetEntry(RazorWind.INSTANCE, LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(SwordsDance.INSTANCE, LearnsetEntry.Source.TM, 3));
        LEARNSET.add(new LearnsetEntry(Whirlwind.INSTANCE, LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Farfetchd(String nickname) {
        super(
            "Farfetch'd",
            83,
            "Normal",
            "Flying",
            5,
            52,
            90,
            55,
            58,
            62,
            60
        );

        this.setNickname(nickname);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Farfetch'd yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
