package pokemonGame.mons;
import pokemonGame.Pokemon;

public class Charmeleon extends Pokemon {
    public Charmeleon(String name) {
        super("Charmeleon", 2, "Fire", null,
        5, 58, 64, 58,
        80, 65, 80);

        this.setName(name);

        int[] evYield = {0, 0, 0, 1, 0, 1}; // Charmeleon yields 1 EV point in Special Attack and 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

}
