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

public class Dewgong extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(
                Growl.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Headbutt.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                AuroraBeam.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Growl.INSTANCE,
                Source.LEVEL,
                30
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                AuroraBeam.INSTANCE,
                Source.LEVEL,
                35
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Rest.INSTANCE,
                Source.LEVEL,
                44
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                TakeDown.INSTANCE,
                Source.LEVEL,
                50
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                IceBeam.INSTANCE,
                Source.LEVEL,
                56
            )
        );

        // HM moves
        LEARNSET.add(
            new LearnsetEntry(
                Surf.INSTANCE,
                Source.HM,
                3
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Strength.INSTANCE,
                Source.HM,
                4
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
                HornDrill.INSTANCE,
                Source.TM,
                7
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

    public Dewgong(String nickname) {
        super(
            PokeSpecies.DEWGONG,
            87,
            Type.WATER,
            Type.ICE,
            5,
            90,
            70,
            80,
            70,
            95,
            70
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_DEFENSE, 2); // Dewgong yields 2 EV points in Special Defense when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
