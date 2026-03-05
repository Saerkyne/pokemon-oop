package pokemonGame.mons;
import pokemonGame.Pokemon;

public class Blastoise extends Pokemon {
    public Blastoise(String name) {
        super("Blastoise", 3, "Water", null,
        5, 79, 83, 100,
        85, 105, 78);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 3, 0}; // Blastoise yields 3 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

}
