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

        

        // Teach Pikachu some moves from its learnset
        /*for (int i = 0; i < 4; i++) {
            LearnsetEntry.teachFromLearnset(pikachu);
        }
        
        pikachu.getMoveset().forEach(move -> System.out.println(pikachu.getName() + " learned " + move.getMoveName()));
        */

        

        // Test for setting IV and EV values and calculating current HP
        
        garchomp.setEvHp(74);
        int currentHp = garchomp.calcCurrentHp(garchomp.getHpBaseStat(), garchomp.getLevel(), garchomp.getIvHp(), garchomp.getEvHp());
        System.out.println(garchomp.getName() + "'s current HP: " + currentHp + " from base HP: " + garchomp.getHpBaseStat() + ", IV HP: " + garchomp.getIvHp() + ", EV HP: " + garchomp.getEvHp());

        // Test for calculating current Attack stat
        
        garchomp.setEvAttack(190);
        double nature = 1.; // Assuming a neutral nature for this example
        int currentAttack = garchomp.calcCurrentStat(garchomp.getAttackBaseStat(), garchomp.getLevel(), garchomp.getIvAttack(), garchomp.getEvAttack(), 1.1);
        System.out.println(garchomp.getName() + "'s current Attack: " + currentAttack + " from base Attack: " + garchomp.getAttackBaseStat() + ", IV Attack: " + garchomp.getIvAttack() + ", EV Attack: " + garchomp.getEvAttack() + ", Nature: " + 1.1);

        // Test for calculating other stats
        
        garchomp.setEvDefense(91);
        int currentDefense = garchomp.calcCurrentStat(garchomp.getDefenseBaseStat(), garchomp.getLevel(), garchomp.getIvDefense(), garchomp.getEvDefense(), nature);
        System.out.println(garchomp.getName() + "'s current Defense: " + currentDefense + " from base Defense: " + garchomp.getDefenseBaseStat() + ", IV Defense: " + garchomp.getIvDefense() + ", EV Defense: " + garchomp.getEvDefense() + ", Nature: " + nature);

        
        garchomp.setEvSpecialAttack(48);
        int currentSpecialAttack = garchomp.calcCurrentStat(garchomp.getSpecialAttackBaseStat(), garchomp.getLevel(), garchomp.getIvSpecialAttack(), garchomp.getEvSpecialAttack(), 0.9);
        System.out.println(garchomp.getName() + "'s current Special Attack: " + currentSpecialAttack + " from base Special Attack: " + garchomp.getSpecialAttackBaseStat() + ", IV Special Attack: " + garchomp.getIvSpecialAttack() + ", EV Special Attack: " + garchomp.getEvSpecialAttack() + ", Nature: " + 0.9);

        
        garchomp.setEvSpecialDefense(84);
        int currentSpecialDefense = garchomp.calcCurrentStat(garchomp.getSpecialDefenseBaseStat(), garchomp.getLevel(), garchomp.getIvSpecialDefense(), garchomp.getEvSpecialDefense(), nature);
        System.out.println(garchomp.getName() + "'s current Special Defense: " + currentSpecialDefense + " from base Special Defense: " + garchomp.getSpecialDefenseBaseStat() + ", IV Special Defense: " + garchomp.getIvSpecialDefense() + ", EV Special Defense: " + garchomp.getEvSpecialDefense() + ", Nature: " + nature);

        
        garchomp.setEvSpeed(23);
        int currentSpeed = garchomp.calcCurrentStat(garchomp.getSpeedBaseStat(), garchomp.getLevel(), garchomp.getIvSpeed(), garchomp.getEvSpeed(), nature);
        System.out.println(garchomp.getName() + "'s current Speed: " + currentSpeed + " from base Speed: " + garchomp.getSpeedBaseStat() + ", IV Speed: " + garchomp.getIvSpeed() + ", EV Speed: " + garchomp.getEvSpeed() + ", Nature: " + nature);



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
