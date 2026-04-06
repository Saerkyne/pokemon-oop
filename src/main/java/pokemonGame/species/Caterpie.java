package pokemonGame.species;

import pokemonGame.moves.*;
import pokemonGame.core.Stat;
import pokemonGame.core.StatCalculator;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.LearnsetEntry;
import pokemonGame.model.Pokemon;
import pokemonGame.model.LearnsetEntry.Source;

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
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
