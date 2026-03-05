package pokemonGame;
//import java.util.Scanner;
import pokemonGame.mons.*;
//import java.util.ArrayList;
//import pokemonGame.moves.*;

public class App {
    public static void main(String[] args) throws Exception {
        //Trainer trainer = createTrainer();

        //selectFromAvailablePokemon();

        Pokemon chuchu = createPokemon("chuchu");

        System.out.println("Attack IV: " + chuchu.getIvAttack());
        System.out.println("Defense IV: " + chuchu.getIvDefense());
        System.out.println("Special Attack IV: " + chuchu.getIvSpecialAttack());
        System.out.println("Special Defense IV: " + chuchu.getIvSpecialDefense());
        System.out.println("Speed IV: " + chuchu.getIvSpeed());
        System.out.println("HP IV: " + chuchu.getIvHp());


        //Display the Team
        /*System.out.println("Your team:");
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
        }*/

        
        

        // Teach Pikachu some moves from its learnset
        /*for (int i = 0; i < 4; i++) {
            LearnsetEntry.teachFromLearnset(pikachu);
        }
        
        pikachu.getMoveset().forEach(move -> System.out.println(pikachu.getName() + " learned " + move.getMoveName()));
        */

        

        
        

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

        //scanner.close();

    }

    public static Trainer createTrainer() {
        // This method can be used to create a trainer and set up their team
        // Create a Trainer and ask for their name
        Trainer trainer = new Trainer("Ash Ketchum");
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

    public static Pokemon createPokemon(String species) {
        // This method can be used to create a new Pokémon instance based on user input for species, level, and other attributes.  
        // It can return the newly created Pokémon instance.
        // Create a Pokemon instance for testing
        
        
        Pokemon chuchu = new Bulbasaur();

        

        
        

        return chuchu; // Placeholder return value
    }
}
