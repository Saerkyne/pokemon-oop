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

public class Muk extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(
                Disable.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PoisonGas.INSTANCE,
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
                PoisonGas.INSTANCE,
                Source.LEVEL,
                30
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Minimize.INSTANCE,
                Source.LEVEL,
                33
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Sludge.INSTANCE,
                Source.LEVEL,
                37
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Harden.INSTANCE,
                Source.LEVEL,
                45
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Screech.INSTANCE,
                Source.LEVEL,
                53
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                AcidArmor.INSTANCE,
                Source.LEVEL,
                60
            )
        );

        // TM Moves
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
                Substitute.INSTANCE,
                Source.TM,
                50
            )
        );
    }

    public Muk(String nickname) {
        super(
            PokeSpecies.MUK,
            89,
            Type.POISON,
            Type.NONE,
            5,
            105,
            105,
            75,
            65,
            100,
            50
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.HP, 1);     // Muk yields 1 EV point in HP when defeated
        this.setEvYield(Stat.ATTACK, 1); // and 1 EV point in Attack when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
