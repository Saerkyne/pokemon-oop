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

public class Persian extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Bite.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Growl.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Scratch.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Screech.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Bite.INSTANCE,
                Source.LEVEL,
                12
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PayDay.INSTANCE,
                Source.LEVEL,
                17
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Screech.INSTANCE,
                Source.LEVEL,
                24
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                FurySwipes.INSTANCE,
                Source.LEVEL,
                37
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Slash.INSTANCE,
                Source.LEVEL,
                51
            )
        );

        // TM moves
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
                HyperBeam.INSTANCE,
                Source.TM,
                15
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PayDay.INSTANCE,
                Source.TM,
                16
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
                Thunderbolt.INSTANCE,
                Source.TM,
                24
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Thunder.INSTANCE,
                Source.TM,
                25
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
                Swift.INSTANCE,
                Source.TM,
                39
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

    public Persian(String nickname) {
        super(
            PokeSpecies.PERSIAN,
            53,
            Type.NORMAL,
            Type.NONE,
            5,
            65,
            70,
            60,
            65,
            65,
            115
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPEED, 2);
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
