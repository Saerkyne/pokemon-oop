package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import java.util.List;

public class Beedrill extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET =
        new java.util.ArrayList<>();

    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(
                FuryAttack.INSTANCE,
                Source.LEVEL,
                1)
        );
        LEARNSET.add(
            new LearnsetEntry(
                FuryAttack.INSTANCE,
                Source.LEVEL,
                12)
        );
        LEARNSET.add(
            new LearnsetEntry(
                FocusEnergy.INSTANCE,
                Source.LEVEL,
                16)
        );
        LEARNSET.add(
            new LearnsetEntry(
                Twineedle.INSTANCE,
                Source.LEVEL,
                20)
        );
        LEARNSET.add(
            new LearnsetEntry(
                Rage.INSTANCE,
                Source.LEVEL,
                25)
        );
        LEARNSET.add(
            new LearnsetEntry(
                PinMissile.INSTANCE,
                Source.LEVEL,
                30)
        );
        LEARNSET.add(
            new LearnsetEntry(
                Agility.INSTANCE,
                Source.LEVEL,
                35)
        );

        // HM moves
        LEARNSET.add(
            new LearnsetEntry(
                Cut.INSTANCE,
                Source.HM,
                1)
        );

        // Pre-evolution moves
        LEARNSET.add(
            new LearnsetEntry(
                Harden.INSTANCE,
                Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PoisonSting.INSTANCE,
                Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StringShot.INSTANCE,
                Source.PRE_EVOLUTION,
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

    public Beedrill(String nickname) {
        super(
            PokeSpecies.BEEDRILL,
            15,
            Type.BUG,
            Type.POISON,
            5,
            65,
            90,
            40,
            45,
            80,
            75
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.ATTACK, 2); // Beedrill yields 2 EV points in Attack when defeated
        this.setEvYield(Stat.SPECIAL_DEFENSE, 1); // Beedrill yields 1 EV point in Special Defense when defeated
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
