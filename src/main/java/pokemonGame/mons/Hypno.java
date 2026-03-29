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

public class Hypno extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(
                Confusion.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Disable.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Hypnosis.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Pound.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Disable.INSTANCE,
                Source.LEVEL,
                12
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Confusion.INSTANCE,
                Source.LEVEL,
                17
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Headbutt.INSTANCE,
                Source.LEVEL,
                24
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PoisonGas.INSTANCE,
                Source.LEVEL,
                33
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Psychic.INSTANCE,
                Source.LEVEL,
                37
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Meditate.INSTANCE,
                Source.LEVEL,
                43
            )
        );

        // HM Moves
        LEARNSET.add(
            new LearnsetEntry(
                Flash.INSTANCE,
                Source.HM,
                5
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
                Psychic.INSTANCE,
                Source.TM,
                29
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Teleport.INSTANCE,
                Source.TM,
                30
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
                Metronome.INSTANCE,
                Source.TM,
                35
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
                DreamEater.INSTANCE,
                Source.TM,
                42
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
                ThunderWave.INSTANCE,
                Source.TM,
                45
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Psywave.INSTANCE,
                Source.TM,
                46
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                TriAttack.INSTANCE,
                Source.TM,
                49
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

    public Hypno(String nickname) {
        super(
            PokeSpecies.HYPNO,
            97,
            Type.PSYCHIC,
            Type.NONE,
            5,
            85,
            73,
            70,
            73,
            115,
            67
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_DEFENSE, 2); // Hypno yields 2 EV points in Special Defense when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
