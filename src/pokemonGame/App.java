package pokemonGame;
import pokemonGame.mons.*;
//import pokemonGame.moves.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Create a Garchomp
        
        Pokemon garchomp = new Garchomp();

        
        // Set base Stats for Garchomp
        garchomp.setLevel(78);
        garchomp.generateRandomIVs();
        System.out.println(garchomp.getNature().getDisplayName());


        

        // Teach Pikachu some moves from its learnset
        /*for (int i = 0; i < 4; i++) {
            LearnsetEntry.teachFromLearnset(pikachu);
        }
        
        pikachu.getMoveset().forEach(move -> System.out.println(pikachu.getName() + " learned " + move.getMoveName()));
        */

        

        // Test for setting IV and EV values and calculating current HP
        garchomp.setEvHp(74);
        garchomp.setEvAttack(190);
        garchomp.setEvSpeed(23);
        garchomp.setEvDefense(91);
        garchomp.setEvSpecialAttack(48);
        garchomp.setEvSpecialDefense(84);
        garchomp.setEvSpeed(23);

        
        garchomp.calculateCurrentStats();
        

        System.out.println(garchomp.getName() + " has the following IVs:");
        System.out.println("HP IV: " + garchomp.getIvHp()); 
        System.out.println("Attack IV: " + garchomp.getIvAttack());
        System.out.println("Defense IV: " + garchomp.getIvDefense());
        System.out.println("Special Attack IV: " + garchomp.getIvSpecialAttack());
        System.out.println("Special Defense IV: " + garchomp.getIvSpecialDefense());
        System.out.println("Speed IV: " + garchomp.getIvSpeed());
        System.out.println(garchomp.getName() + " has the following EVs:");
        System.out.println("HP EV: " + garchomp.getEvHp()); 
        System.out.println("Attack EV: " + garchomp.getEvAttack());
        System.out.println("Defense EV: " + garchomp.getEvDefense());
        System.out.println("Special Attack EV: " + garchomp.getEvSpecialAttack());
        System.out.println("Special Defense EV: " + garchomp.getEvSpecialDefense());
        System.out.println("Speed EV: " + garchomp.getEvSpeed());
        System.out.println(garchomp.getName() + " has a current HP of: " + garchomp.getCurrentHP());
        System.out.println(garchomp.getName() + " has a current Attack stat of: " + garchomp.getCurrentAttack());
        System.out.println(garchomp.getName() + " has a current Defense stat of: " + garchomp.getCurrentDefense());
        System.out.println(garchomp.getName() + " has a current Special Attack stat of: " + garchomp.getCurrentSpecialAttack());
        System.out.println(garchomp.getName() + " has a current Special Defense stat of: " + garchomp.getCurrentSpecialDefense());
        System.out.println(garchomp.getName() + " has a current Speed stat of: " + garchomp.getCurrentSpeed());

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

        /*System.out.println(pikachu.getName() + " used Thunder against " + gengar.getName() + "!");
        Attack attack = new Attack() {};
        float damageDone = attack.calculateDamage(pikachu, gengar, new Thunder());
        int roundedDamage = (int) Math.floor(damageDone);
        System.out.println("Damage dealt: " + roundedDamage);  */

    }
}
