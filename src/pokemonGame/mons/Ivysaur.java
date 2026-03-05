package pokemonGame.mons;
import pokemonGame.Pokemon;

public class Ivysaur extends Pokemon {
    public Ivysaur(String name) {
        super("Ivysaur", 2, "Grass", "Poison",
        5, 60, 62, 63,
        80, 80, 60);

        this.setName(name);

        int[] evYield = {0, 0, 0, 1, 1, 0}; // Ivysaur yields 1 EV point in Special Attack and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }
}
