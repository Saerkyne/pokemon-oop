package pokemonGame.mons;
import pokemonGame.Pokemon;

public class Squirtle extends Pokemon {
    public Squirtle(String name) {
        super("Squirtle", 1, "Water", null,
        5, 44, 48, 65, 50,
        64, 43);
        
        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Squirtle yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    
    }
}
