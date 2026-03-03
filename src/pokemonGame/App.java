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

        pikachu.setLevel(100);
        gengar.setLevel(100);
        


        //Iterate through Pikachu's moves and calculate effectiveness against Gengar instead of hardcoded attacks
        /*for (Move move : pikachu.getMoveset()) {
            System.out.println(pikachu.getName() + " used " + move.getMoveName() + " against " + gengar.getName() + "!");
            Attack attack = new Attack() {};
            float damageDone = attack.calculateDamage(pikachu, gengar, move);

            int roundedDamage = (int) Math.floor(damageDone);
            
            System.out.println(move.getMoveName() + " is a " + move.getType() + " type move and " + gengar.getName() + " is a " + gengar.getTypePrimary() + "/" + gengar.getTypeSecondary() + " type Pokemon.");
            System.out.println("Damage dealt: " + damageDone);
            System.out.println("Rounded damage: " + roundedDamage);
            System.out.println();
        }*/

        System.out.println(pikachu.getName() + " used Thunder against " + gengar.getName() + "!");
        Attack attack = new Attack() {};
        float damageDone = attack.calculateDamage(pikachu, gengar, new Thunder());
        int roundedDamage = (int) Math.floor(damageDone);
        System.out.println("Damage dealt: " + roundedDamage);  

    }
}
