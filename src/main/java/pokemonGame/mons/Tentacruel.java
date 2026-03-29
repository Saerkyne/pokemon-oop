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

public class Tentacruel extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Acid.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Supersonic.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Wrap.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Supersonic.INSTANCE,
                Source.LEVEL,
                7
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Wrap.INSTANCE,
                Source.LEVEL,
                13
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PoisonSting.INSTANCE,
                Source.LEVEL,
                18
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                WaterGun.INSTANCE,
                Source.LEVEL,
                22
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Constrict.INSTANCE,
                Source.LEVEL,
                27
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Barrier.INSTANCE,
                Source.LEVEL,
                35
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Screech.INSTANCE,
                Source.LEVEL,
                43
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                HydroPump.INSTANCE,
                Source.LEVEL,
                50
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
        LEARNSET.add(
            new LearnsetEntry(
                Surf.INSTANCE,
                Source.HM,
                3
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
                BubbleBeam.INSTANCE,
                Source.TM,
                11
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                WaterGun.INSTANCE,
                Source.TM,
                12
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                IceBeam.INSTANCE,
                Source.TM,
                13
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Blizzard.INSTANCE,
                Source.TM,
                14
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
                Reflect.INSTANCE,
                Source.TM,
                33
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

    public Tentacruel(String nickname) {
        super(
            PokeSpecies.TENTACRUEL,
            73,
            Type.WATER,
            Type.POISON,
            5,
            80,
            70,
            65,
            80,
            120,
            100
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_DEFENSE, 2);
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
