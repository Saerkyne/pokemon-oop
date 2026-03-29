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

public class Marowak extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(
                BoneClub.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                FocusEnergy.INSTANCE,
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
                Leer.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Leer.INSTANCE,
                Source.LEVEL,
                25
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                FocusEnergy.INSTANCE,
                Source.LEVEL,
                33
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Thrash.INSTANCE,
                Source.LEVEL,
                41
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Bonemerang.INSTANCE,
                Source.LEVEL,
                48
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Rage.INSTANCE,
                Source.LEVEL,
                55
            )
        );

        // HM Moves
        LEARNSET.add(
            new LearnsetEntry(
                Strength.INSTANCE,
                Source.HM,
                4
            )
        );

        // TM Moves
        LEARNSET.add(
            new LearnsetEntry(
                MegaPunch.INSTANCE,
                Source.TM,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                MegaKick.INSTANCE,
                Source.TM,
                5
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
                Submission.INSTANCE,
                Source.TM,
                17
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Counter.INSTANCE,
                Source.TM,
                18
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SeismicToss.INSTANCE,
                Source.TM,
                19
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
                Earthquake.INSTANCE,
                Source.TM,
                26
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Fissure.INSTANCE,
                Source.TM,
                27
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Dig.INSTANCE,
                Source.TM,
                28
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
                FireBlast.INSTANCE,
                Source.TM,
                38
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

    public Marowak(String nickname) {
        super(
            PokeSpecies.MAROWAK,
            105,
            Type.GROUND,
            Type.NONE,
            5,
            60,
            80,
            110,
            50,
            80,
            45
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.DEFENSE, 2); // Marowak yields 2 EV points in Defense when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
