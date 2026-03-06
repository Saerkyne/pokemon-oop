package pokemonGame;
//import java.util.Scanner;
import pokemonGame.mons.*;
//import java.util.ArrayList;
//import pokemonGame.moves.*;
import java.util.TreeMap;

public class App {
    public static void main(String[] args) throws Exception {
        Trainer trainer = createTrainer("Joel");

        /*
        Personal Notes: when creating a pokemon this way, the Pokemon object created is the same
        as the one then added to the trainer. In other words, in this example, "Bulby" is created
        and added to the trainer. When we reference the trainer's Bulby, we can reference the initial 
        "Bulby" Pokemon object, or we can reference the trainer's team object via 
        trainer.getTeam()get(0) (assuming Bulby is in the first slot). Stat changes 
        using one reference also apply to the other reference.
        */
        Pokemon Bulby = createPokemon("Bulbasaur", "Bulby");
        Pokemon Chard = createPokemon("Charmander", "Chard");
        Pokemon Squirt = createPokemon("Squirtle", "Squirt");

        trainer.addPokemonToTeam(Bulby);
        trainer.addPokemonToTeam(Chard);
        trainer.addPokemonToTeam(Squirt);
        trainer.getTeam().forEach(p -> System.out.println(p.getName() + " added to team!"));
        


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


        Pokemon bulbyTest = trainer.getTeam().get(0); // Assuming Bulby is the first Pokemon in the team
        // Is bulbyTest the same as Bulby?
        System.out.println("Is bulbyTest the same as Bulby? " + (bulbyTest == Bulby)); // Should print true

        
        System.out.println("Testing Attack Stat saving for " + bulbyTest.getName() + ": " + bulbyTest.getCurrentAttack());
        System.out.println("Bulby's current attack stat: " + Bulby.getCurrentAttack()); // Should reflect the change made to bulbyTest's attack base

        System.out.println(bulbyTest.getLevel());
        System.out.println(Bulby.getLevel());

        Bulby.setLevel(100);
        System.out.println(bulbyTest.getLevel());
        System.out.println(Bulby.getLevel());


        

        bulbyTest.setAttackBase(180); // Change the attack base stat for bulbyTest
        System.out.println("Testing Attack Stat saving for " + bulbyTest.getName() + " after setting base to 180: " + bulbyTest.getCurrentAttack());
        System.out.println("Testing Attack Stat saving for Bulby after setting base to 180 on bulbyTest: " + Bulby.getCurrentAttack()); // Should reflect the change made to bulbyTest's attack base
        LearnsetEntry.teachFromLearnset(bulbyTest); // Teach the first move from the learnset to the first Pokemon in the team
        bulbyTest.getMoveset().forEach(move -> System.out.println(bulbyTest.getName() + " learned " + move.getMove().getMoveName()));
        Bulby.getMoveset().forEach(move -> System.out.println(Bulby.getName() + " knows " + move.getMove().getMoveName())); // Should show the same move learned by bulbyTest
        System.out.println(bulbyTest.getName() + " has a " + bulbyTest.getNature().getDisplayName() + " nature!");
        System.out.println(Bulby.getName() + " has a " + Bulby.getNature().getDisplayName() + " nature!"); // Should show the same nature as bulbyTest  


        
        

        // Teach some moves from its learnset
        /*for (int i = 0; i < 4; i++) {
            LearnsetEntry.teachFromLearnset(pikachu);
        }
        
        pikachu.getMoveset().forEach(move -> System.out.println(pikachu.getName() + " learned " + move.getMoveName()));
        */

        

        
        

        //Iterate through Pikachu's moves and calculate effectiveness against Gengar instead of hardcoded attacks
        /*for (Move move : pikachu.getMoveset()) {
            System.out.println(pikachu.getName() + " used " + move.getMoveName() + " against " + gengar.getName() + "!");
            Attack attack = new Attack();
            float damageDone = attack.calculateDamage(pikachu, gengar, move);

            int roundedDamage = (int) Math.floor(damageDone);
            
            System.out.println(move.getMoveName() + " is a " + move.getType() + " type move and " + gengar.getName() + " is a " + gengar.getTypePrimary() + "/" + gengar.getTypeSecondary() + " type Pokemon.");
            System.out.println("Damage dealt: " + damageDone);
            System.out.println("Rounded damage: " + roundedDamage);
            System.out.println();
        }*/

        /*System.out.println(pikachu.getName() + " used Thunder against " + gengar.getName() + "!");
        Attack attack = new Attack();
        float damageDone = attack.calculateDamage(pikachu, gengar, new Thunder());
        int roundedDamage = (int) Math.floor(damageDone);
        System.out.println("Damage dealt: " + roundedDamage);  */

        //scanner.close();

    }

    public static Trainer createTrainer(String name) {
        // This method can be used to create a trainer and set up their team
        // Create a Trainer and ask for their name
        Trainer trainer = new Trainer(name);
        //Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter your name, Trainer: ");
        //String trainerName = scanner.nextLine();
        //trainer.setName(trainerName);

        System.out.println(trainer.getName() + ", welcome to the world of Pokémon!");

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("You can have up to 6 Pokémon on your team. Let's start by choosing your first Pokémon!");

        return trainer;
    }

    public static Pokemon selectFromAvailablePokemon(Trainer trainer) {
        // This method can be used to list available Pokémon species for the player to choose from, 
        // and allow them to select one by name.  It can return a new instance of the chosen Pokémon.
        // Allow the player to choose a Pokémon by name from the generated list of available Pokémon.  
        // This can be done using a simple text input prompt.
       /*  String[] pokemonOptions = { "Bulbasaur", "Ivysaur","Venusaur", "Charmander",
        "Charmeleon","Charizard","Squirtle","Wartortle","Blastoise","Caterpie",
        "Metapod","Butterfree","Weedle","Kakuna","Beedrill","Pidgey","Pidgeotto",
        "Pidgeot","Rattata","Raticate","Spearow","Fearow","Ekans","Arbok","Pikachu",
        "Raichu","Sandshrew","Sandslash","Nidoran♀","Nidorina","Nidoqueen","Nidoran♂",
        "Nidorino","Nidoking","Clefairy","Clefable","Vulpix","Ninetales","Jigglypuff",
        "Wigglytuff","Zubat","Golbat","Oddish","Gloom","Vileplume","Paras","Parasect",
        "Venonat","Venomoth","Diglett","Dugtrio","Meowth","Persian","Psyduck",
        "Golduck","Mankey","Primeape","Growlithe","Arcanine","Poliwag","Poliwhirl",
        "Poliwrath","Abra","Kadabra","Alakazam","Machop","Machoke","Machamp",
        "Bellsprout","Weepinbell","Victreebel","Tentacool","Tentacruel","Geodude",
        "Graveler","Golem","Ponyta","Rapidash","Slowpoke","Slowbro","Magnemite",
        "Magneton","Farfetch'd","Doduo","Dodrio","Seel","Dewgong","Grimer","Muk",
        "Shellder","Cloyster","Gastly","Haunter","Gengar","Onix","Drowzee","Hypno",
        "Krabby","Kingler","Voltorb","Electrode","Exeggcute","Exeggutor","Cubone",
        "Marowak","Hitmonlee","Hitmonchan","Lickitung","Koffing","Weezing","Rhyhorn",
        "Rhydon","Chansey","Tangela","Kangaskhan","Horsea","Seadra","Goldeen","Seaking",
        "Staryu","Starmie","Mr. Mime","Scyther","Jynx","Electabuzz","Magmar","Pinsir",
        "Tauros","Magikarp","Gyarados","Lapras","Ditto","Eevee","Vaporeon","Jolteon",
        "Flareon","Porygon","Omanyte","Omastar","Kabuto","Kabutops","Aerodactyl",
        "Snorlax","Articuno","Zapdos","Moltres","Dratini","Dragonair","Dragonite",
        "Mewtwo","Mew"
        };*/
        
        //ArrayList<Pokemon> availablePokemon = new ArrayList<>();

        /*for (String pokemon : pokemonOptions) {
            switch (pokemon) {
                case "Bulbasaur":
                    availablePokemon.add(new Bulbasaur());
                    break;
                case "Ivysaur":
                    availablePokemon.add(new Ivysaur());
                    break;
                case "Venusaur":
                    availablePokemon.add(new Venusaur());
                    break;
                case "Charmander":
                    availablePokemon.add(new Charmander());
                    break;
                case "Charmeleon":
                    availablePokemon.add(new Charmeleon());
                    break;
                case "Charizard":
                    availablePokemon.add(new Charizard());
                    break;
                case "Squirtle":
                    availablePokemon.add(new Squirtle());
                    break;
                case "Wartortle":
                    availablePokemon.add(new Wartortle());
                    break;
                case "Blastoise":
                    availablePokemon.add(new Blastoise());
                    break;
                case "Caterpie":
                    availablePokemon.add(new Caterpie());
                    break;
            }
        }*/

        

        return null; // Placeholder return value
    }

    public static Pokemon createPokemon(String species, String name) {
        // This method can be used to create a new Pokémon instance based on user input for species, level, and other attributes.  
        // It can return the newly created Pokémon instance.
        
        Pokemon createdMon = null;
        TreeMap<Integer, String> pokemonOptions = new TreeMap<>();
        pokemonOptions.put(1, "Bulbasaur");
        pokemonOptions.put(2, "Ivysaur");
        pokemonOptions.put(3, "Venusaur");
        pokemonOptions.put(4, "Charmander");
        pokemonOptions.put(5, "Charmeleon");
        pokemonOptions.put(6, "Charizard");
        pokemonOptions.put(7, "Squirtle");
        pokemonOptions.put(8, "Wartortle");
        pokemonOptions.put(9, "Blastoise");

        if (species == null || !pokemonOptions.containsValue(species)) {
            System.out.println("Invalid species selected. Please choose a valid Pokémon.");
            return null;
        }

        switch (species) {
            case "Bulbasaur":
                createdMon = new Bulbasaur(name);
                break;
            case "Ivysaur":
                createdMon = new Ivysaur(name);
                break;
            case "Venusaur":
                createdMon = new Venusaur(name);
                break;
            case "Charmander":
                createdMon = new Charmander(name);
                break;
            case "Charmeleon":
                createdMon = new Charmeleon(name);
                break;
            case "Charizard":
                createdMon = new Charizard(name);
                break;
            case "Squirtle":
                createdMon = new Squirtle(name);
                break;
            case "Wartortle":
                createdMon = new Wartortle(name);
                break;
            case "Blastoise":
                createdMon = new Blastoise(name);
                break;
            default:
                System.out.println("Invalid species selected. Please choose a valid Pokémon.");
                return null;
        }
               
        return createdMon; // Placeholder return value
    }
}
