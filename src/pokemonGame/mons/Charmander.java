package pokemonGame.mons;
import pokemonGame.Pokemon;

public class Charmander extends Pokemon {
    public Charmander() {
        super("Charmander", 4, "Fire", "None",
         5, 39, 52, 43,
         60, 50, 65);

         int[] evYield = {0, 0, 0, 0, 0, 1}; // Charmander yields 1 EV point in Speed when defeated
         this.setEvYield(evYield);
    }

}
