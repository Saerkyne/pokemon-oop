package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import java.util.List;

public class Caterpie extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(
                Tackle.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StringShot.INSTANCE,
                Source.LEVEL,
                1
            )
        );
    }

    public Caterpie(String nickname) {
        super(
            PokeSpecies.CATERPIE,
            10,
            Type.BUG,
            Type.NONE,
            5,
            45,
            30,
            35,
            20,
            20,
            45
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.HP, 1); // Caterpie yields 1 EV point in HP when defeated
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
