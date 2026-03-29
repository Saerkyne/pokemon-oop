package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import pokemonGame.StatCalculator;
import java.util.List;

public class Tangela extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Bind.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Constrict.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Absorb.INSTANCE,
                Source.LEVEL,
                29
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PoisonPowder.INSTANCE,
                Source.LEVEL,
                32
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StunSpore.INSTANCE,
                Source.LEVEL,
                36
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SleepPowder.INSTANCE,
                Source.LEVEL,
                39
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Slam.INSTANCE,
                Source.LEVEL,
                45
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Growth.INSTANCE,
                Source.LEVEL,
                49
            )
        );

        // HM moves
        LEARNSET.add(
            new LearnsetEntry(
                Cut.INSTANCE,
                Source.HM,
                1
            )
        );

        // TM moves
        LEARNSET.add(
            new LearnsetEntry(
                SwordsDance.INSTANCE,
                Source.TM,
                3
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Toxic.INSTANCE,
                Source.TM,
                6
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                BodySlam.INSTANCE,
                Source.TM,
                8
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                TakeDown.INSTANCE,
                Source.TM,
                9
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                DoubleEdge.INSTANCE,
                Source.TM,
                10
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                HyperBeam.INSTANCE,
                Source.TM,
                15
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Rage.INSTANCE,
                Source.TM,
                20
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                MegaDrain.INSTANCE,
                Source.TM,
                21
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SolarBeam.INSTANCE,
                Source.TM,
                22
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Mimic.INSTANCE,
                Source.TM,
                31
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                DoubleTeam.INSTANCE,
                Source.TM,
                32
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Bide.INSTANCE,
                Source.TM,
                34
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SkullBash.INSTANCE,
                Source.TM,
                40
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Rest.INSTANCE,
                Source.TM,
                44
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Substitute.INSTANCE,
                Source.TM,
                50
            )
        );
    }

    public Tangela(String nickname) {
        super(
            PokeSpecies.TANGELA,
            114,
            Type.GRASS,
            Type.NONE,
            5,
            65,
            55,
            115,
            100,
            40,
            60
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.DEFENSE, 2);
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
