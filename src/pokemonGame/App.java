package pokemonGame;
import java.util.Scanner;
import pokemonGame.mons.*;
//import pokemonGame.moves.*;

public class App {
    public static void main(String[] args) throws Exception {
        Trainer trainer = createTrainer();

        selectFromAvailablePokemon();
  



        //Display the Team
        System.out.println("Your team:");
        for (Pokemon p : trainer.getTeam()) {
            if (p != null) {
                System.out.println("- " + p.getName());
            }
        }

        //Display team stats
        System.out.println();
        System.out.println("Your team's stats:");
        for (Pokemon p : trainer.getTeam()) {
            if (p != null) {
                System.out.println(p.getName() + ": Level " + p.getLevel() + ", HP " + p.getMaxHP() + ", Attack " + p.getCurrentAttack() + ", Defense " + p.getCurrentDefense() + ", Special Attack " + p.getCurrentSpecialAttack() + ", Special Defense " + p.getCurrentSpecialDefense() + ", Speed " + p.getCurrentSpeed());
            }
        }

        
        // Create a Pokemon instance for testing
        
        Pokemon chuchu = new Pikachu();

        
        // Set base Stats for Garchomp
        chuchu.setLevel(78);
        chuchu.generateRandomIVs();


        

        // Teach Pikachu some moves from its learnset
        /*for (int i = 0; i < 4; i++) {
            LearnsetEntry.teachFromLearnset(pikachu);
        }
        
        pikachu.getMoveset().forEach(move -> System.out.println(pikachu.getName() + " learned " + move.getMoveName()));
        */

        

        // Test for setting IV and EV values and calculating current HP
        chuchu.setEvHp(74);
        chuchu.setEvAttack(190);
        chuchu.setEvSpeed(23);
        chuchu.setEvDefense(91);
        chuchu.setEvSpecialAttack(48);
        chuchu.setEvSpecialDefense(84);
        chuchu.setEvSpeed(23);

        
        chuchu.calculateCurrentStats();
        

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

        scanner.close();

    }

    public static Trainer createTrainer() {
        // This method can be used to create a trainer and set up their team
        // Create a Trainer and ask for their name
        Trainer trainer = new Trainer("Ash Ketchum");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name, Trainer: ");
        String trainerName = scanner.nextLine();
        trainer.setName(trainerName);

        System.out.println(trainer.getName() + ", welcome to the world of Pokémon!");

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("You can have up to 6 Pokémon on your team. Let's start by choosing your first Pokémon!");

        return trainer;
    }

    public static Pokemon selectFromAvailablePokemon(Trainer trainer) {
        // This method can be used to list available Pokémon species for the player to choose from, and allow them to 
        // select one by name.  It can return a new instance of the chosen Pokémon.
        // Allow the player to choose a Pokémon by name from the generated list of available Pokémon.  This can be done using a simple text input prompt.
        System.out.print("Enter the name of the Pokémon you want to choose: ");
        String chosenPokemon = scanner.nextLine();

        // Create a Pokémon based on the player's choice
        Pokemon chosen = null;
        switch (chosenPokemon.toLowerCase()) {
            
            
            case "pikachu":
                chosen = new Pikachu();
                break;
            // Add more cases for other Pokémon species
            default:
                System.out.println("Invalid choice. Defaulting to Pikachu.");
                chosen = new Pikachu();
                break;
        }

        // Generate random IVs and calculate stats for the chosen Pokémon
        chosen.generateRandomIVs();
        chosen.calculateCurrentStats();

        

        //Add the chosen Pokémon to the trainer's team
        trainer.addPokemonToTeam(chosen);

        System.out.println("Do you want to add another Pokémon to your team? (yes/no)");
        String addMore = scanner.nextLine();
        
        while (addMore.equalsIgnoreCase("yes") && trainer.getTeam().size() < 6) {
            System.out.print("Enter the name of the Pokémon you want to choose: ");
            chosenPokemon = scanner.nextLine();

            switch (chosenPokemon.toLowerCase()) {
                case "bulbasaur":
                    chosen = new Bulbasaur();
                    break;
                case "charmander":
                    chosen = new Charmander();
                    break;
                case "pikachu":
                    chosen = new Pikachu();
                    break;
                case "eevee":
                    chosen = new Eevee();
                    break;
                case "gengar":
                    chosen = new Gengar();
                    break;
                case "mewtwo":
                    chosen = new Mewtwo();
                    break;
                case "squirtle":
                    chosen = new Squirtle();
                    break;
                // Add more cases for other Pokémon species
                default:
                    System.out.println("Invalid choice. Defaulting to Pikachu.");
                    chosen = new Pikachu();
                    break;
            }

            chosen.generateRandomIVs();
            chosen.calculateCurrentStats();

            trainer.addPokemonToTeam(chosen);

            if (trainer.getTeam().size() >= 6) {
                System.out.println("Your team is full!");
                break;
            }

            System.out.println("Do you want to add another Pokémon to your team? (yes/no)");
            addMore = scanner.nextLine();
        }

        return null; // Placeholder return value
    }

}
