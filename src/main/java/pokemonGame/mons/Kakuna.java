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

public class Kakuna extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(
                Harden.INSTANCE,
                Source.LEVEL,
                1
            )
        );

        // Pre-Evolution Moves
        LEARNSET.add(
            new LearnsetEntry(
                PoisonSting.INSTANCE,
                Source.PRE_EVOLUTION,
                0
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StringShot.INSTANCE,
                Source.PRE_EVOLUTION,
                0
            )
        );
    }

    public Kakuna(String nickname) {
        super(
            PokeSpecies.KAKUNA,
            14,
            Type.BUG,
            Type.POISON,
            5,
            45,
            25,
            50,
            25,
            25,
            35
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.DEFENSE, 2); // Kakuna yields 2 EV points in Defense when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
