package pokemonGame.mons;
import pokemonGame.Pokemon;

public class Wartortle extends Pokemon {
    public Wartortle(String name) {
        super("Wartortle", 2, "Water", null,
        5, 59, 63, 80,
        65, 80, 58);

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 1, 0}; // Wartortle yields 1 EV point in Defense and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

}
