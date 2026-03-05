package pokemonGame.mons;
import pokemonGame.Pokemon;

public class Bulbasaur extends Pokemon {
    public Bulbasaur() {
        super("Bulbasaur", 1, "Grass", "Poison", 
        5, 45, 49, 49, 
        65, 65, 45);

        int[] evYield = {0, 0, 0, 0, 1, 0}; // Bulbasaur yields 1 EV point in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }
}
