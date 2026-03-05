package pokemonGame.mons;
import pokemonGame.Pokemon;

public class Venusaur extends Pokemon {
    public Venusaur(String name) {
        super("Venusaur", 3, "Grass", "Poison",
        5, 80, 82, 83,
        100, 100, 80);

        this.setName(name);

        int[] evYield = {0, 0, 0, 2, 1, 0}; // Venusaur yields 2 EV points in Special Attack and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }
}
