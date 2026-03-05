package pokemonGame.mons;
import pokemonGame.Pokemon;

public class Charizard extends Pokemon {
    public Charizard(String name) {
        super("Charizard", 3, "Fire", "Flying",
        5, 78, 84, 78,
        109, 85, 100);

        this.setName(name);

        int[] evYield = {0, 0, 0, 3, 0, 0}; // Charizard yields 3 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }
}
