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

public class Golem extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(
                DefenseCurl.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Tackle.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                DefenseCurl.INSTANCE,
                Source.LEVEL,
                11
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                RockThrow.INSTANCE,
                Source.LEVEL,
                16
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SelfDestruct.INSTANCE,
                Source.LEVEL,
                21
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Harden.INSTANCE,
                Source.LEVEL,
                29
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Earthquake.INSTANCE,
                Source.LEVEL,
                36
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Explosion.INSTANCE,
                Source.LEVEL,
                43
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
                Metronome.INSTANCE,
                Source.TM,
                35
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SelfDestruct.INSTANCE,
                Source.TM,
                36
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
                Rest.INSTANCE,
                Source.TM,
                44
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Explosion.INSTANCE,
                Source.TM,
                47
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                RockSlide.INSTANCE,
                Source.TM,
                48
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

    public Golem(String nickname) {
        super(
            PokeSpecies.GOLEM,
            76,
            Type.ROCK,
            Type.GROUND,
            5,
            80,
            120,
            130,
            55,
            65,
            45
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.DEFENSE, 3); // Golem yields 3 EV points in Defense when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
