package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import java.util.List;

public class Metapod extends Pokemon {

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
                StringShot.INSTANCE,
                Source.PRE_EVOLUTION,
                0
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Tackle.INSTANCE,
                Source.PRE_EVOLUTION,
                0
            )
        );
    }

    public Metapod(String nickname) {
        super(
            PokeSpecies.METAPOD,
            11,
            Type.BUG,
            Type.NONE,
            5,
            50,
            20,
            55,
            25,
            25,
            30
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.DEFENSE, 2); // Metapod yields 2 EV points in Defense when defeated
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
