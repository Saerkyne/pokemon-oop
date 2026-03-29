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

public class Cloyster extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(
                Withdraw.INSTANCE,
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
                Clamp.INSTANCE,
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
                SpikeCannon.INSTANCE,
                Source.LEVEL,
                50
            )
        );

        // Pre-evolution moves
        LEARNSET.add(
            new LearnsetEntry(
                Tackle.INSTANCE,
                Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Leer.INSTANCE,
                Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                IceBeam.INSTANCE,
                Source.PRE_EVOLUTION,
                1
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
                SelfDestruct.INSTANCE,
                Source.TM,
                36
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

    public Cloyster(String nickname) {
        super(
            PokeSpecies.CLOYSTER,
            91,
            Type.WATER,
            Type.ICE,
            5,
            50,
            95,
            180,
            85,
            45,
            70
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.DEFENSE, 2); // Cloyster yields 2 EV points in Defense when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
