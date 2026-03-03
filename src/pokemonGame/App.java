package pokemonGame;
import pokemonGame.mons.*;
import pokemonGame.moves.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Create some Pokemon
        Pokemon pikachu = new Pikachu();
        Pokemon gengar = new Gengar();

        pikachu.addMove(new Thunder());
        pikachu.addMove(new QuickAttack());
        pikachu.addMove(new PoisonSting());
        pikachu.addMove(new Crunch());


        //Iterate through Pikachu's moves and calculate effectiveness against Gengar instead of hardcoded attacks
        for (Move move : pikachu.getMoveset()) {
            Attack attack = new Attack() {};
            float effectiveness = attack.calculateEffectiveness(gengar, move);
            System.out.println(pikachu.getName() + " used " + move.getMoveName() + " against " + gengar.getName() + "!");
            System.out.println(move.getMoveName() + " is a " + move.getType() + " type move and " + gengar.getName() + " is a " + gengar.getTypePrimary() + "/" + gengar.getTypeSecondary() + " type Pokemon.");
            System.out.println("It" + (effectiveness == 0.0 ? " had no effect..." : effectiveness < 1 ? "'s not very effective..." : effectiveness > 1 ? "'s super effective!" : "'s effective."));
            System.out.println("Damage multiplier: " + effectiveness);
            System.out.println();
        }
    }
}
