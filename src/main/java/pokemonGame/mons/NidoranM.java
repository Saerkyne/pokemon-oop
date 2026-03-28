package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.SpeciesAliases;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import java.util.List;

public class NidoranM extends Pokemon {

    @SpeciesAliases({"nidoranm", "nidoran♂", "nidoran m", "m nidoran", "mnidoran"}) // Handle common variations in naming

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Leer.INSTANCE,
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
                HornAttack.INSTANCE,
                Source.LEVEL,
                8
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PoisonSting.INSTANCE,
                Source.LEVEL,
                14
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                FocusEnergy.INSTANCE,
                Source.LEVEL,
                21
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                FuryAttack.INSTANCE,
                Source.LEVEL,
                29
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                HornDrill.INSTANCE,
                Source.LEVEL,
                36
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                DoubleKick.INSTANCE,
                Source.LEVEL,
                43
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
                Blizzard.INSTANCE,
                Source.TM,
                14
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

    public NidoranM(String nickname) {
        super(
            PokeSpecies.NIDORANM,
            32,
            Type.POISON,
            Type.NONE,
            5,
            46,
            57,
            40,
            40,
            40,
            50
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.ATTACK, 1);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
