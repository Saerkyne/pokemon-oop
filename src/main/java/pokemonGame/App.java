package pokemonGame;
import java.util.List;
import java.io.Console;
import pokemonGame.db.*;
// For testing only: 

public class App {
    public static void main(String[] args) throws Exception {
        long discordID = 123456789012345678L; // Placeholder Discord ID
        String discordUsername = "TestUserDiscord"; // Placeholder Discord username
        String trainerName = "TestUser"; // Placeholder trainer name

        Trainer trainer = createTrainer(trainerName, discordID, discordUsername);

        Pokemon bulby = Pokemon.createPokemon("Bulbasaur", "Bulby", trainer);
        // Insert the new Pokemon into the database and set its ID
        PokemonCRUD pokemonCRUD = new PokemonCRUD();
        int bulbyId = pokemonCRUD.createDBPokemon(bulby);
        bulby.setId(bulbyId);

        

        
        System.out.println();
        

        System.out.println("Pulling data from the database for " + bulby.getName() + "...");
        Pokemon retrievedBulby = trainer.getPokemonByInstanceId(bulby.getId());

        System.out.println("Retrieved Pokemon:");
        System.out.println("- Name: " + retrievedBulby.getName());
        System.out.println("- Species: " + retrievedBulby.getSpecies());
        System.out.println("- Level: " + retrievedBulby.getLevel());
        System.out.println("- Nature: " + retrievedBulby.getNature().getDisplayName());
        System.out.println("- Current HP: " + retrievedBulby.getCurrentHP());
        System.out.println("- IVs: HP " + retrievedBulby.getIvHp() + ", Attack " + retrievedBulby.getIvAttack() + ", Defense " + retrievedBulby.getIvDefense() + ", Special Attack " + retrievedBulby.getIvSpecialAttack() + ", Special Defense " + retrievedBulby.getIvSpecialDefense() + ", Speed " + retrievedBulby.getIvSpeed());
        System.out.println("- EVs: HP " + retrievedBulby.getEvHp() + ", Attack " + retrievedBulby.getEvAttack() + ", Defense " + retrievedBulby.getEvDefense() + ", Special Attack " + retrievedBulby.getEvSpecialAttack() + ", Special Defense " + retrievedBulby.getEvSpecialDefense() + ", Speed " + retrievedBulby.getEvSpeed());
        System.out.println("- Current EXP: " + retrievedBulby.getCurrentExp());


        // Display the Team
        /*
        System.out.println("Your team:");
        for (Pokemon p : trainer.getTeam()) {
            if (p != null) {
                System.out.println("- " + p.getName());
            }
        }
        */


        //Display team stats
        /*
        System.out.println();
        System.out.println("Your team's stats:");
        for (Pokemon p : trainer.getTeam()) {
            if (p != null) {
                System.out.println(p.getName() + ": Level " + p.getLevel() + ", HP " + p.getMaxHP() + ", Attack " + p.getCurrentAttack() + ", Defense " + p.getCurrentDefense() + ", Special Attack " + p.getCurrentSpecialAttack() + ", Special Defense " + p.getCurrentSpecialDefense() + ", Speed " + p.getCurrentSpeed());
            }
        }
        */

        
        
        

        // Display the moves each Pokémon has learned
        /* 
        System.out.println(JoelAbra.getName() + " knows:");
        for (MoveSlot move : JoelAbra.getMoveset()) {
            System.out.println("- " + move.getMove().getMoveName());
        }
    

        System.out.println();
        System.out.println("Your team stats:");
        for (Pokemon p : trainer.getTeam()) {
            if (p != null) {
                System.out.println(p.getName() + ": Level " + p.getLevel() + ",  Max HP " + p.getMaxHP() + ", Current HP " + p.getCurrentHP() + ", Attack " + p.getCurrentAttack() + ", Defense " + p.getCurrentDefense() + ", Special Attack " + p.getCurrentSpecialAttack() + ", Special Defense " + p.getCurrentSpecialDefense() + ", Speed " + p.getCurrentSpeed());
            }
        }
        System.out.println();
        System.out.println("Opponent's team stats:");
        
        System.out.println();
        */



        



        //Iterate through Bulby's moves and calculate effectiveness against Gengar instead of hardcoded attacks
        /*for (Move move : Bulby.getMoveset()) {
            System.out.println(Bulby.getName() + " used " + move.getMoveName() + " against " + gengar.getName() + "!");
            Attack attack = new Attack();
            float damageDone = attack.calculateDamage(Bulby, gengar, move);

            int roundedDamage = (int) Math.floor(damageDone);
            
            System.out.println(move.getMoveName() + " is a " + move.getType() + " type move and " + gengar.getName() + " is a " + gengar.getTypePrimary() + "/" + gengar.getTypeSecondary() + " type Pokemon.");
            System.out.println("Damage dealt: " + damageDone);
            System.out.println("Rounded damage: " + roundedDamage);
            System.out.println();
        }*/

        /*System.out.println(Bulby.getName() + " used Thunder against " + gengar.getName() + "!");
        Attack attack = new Attack();
        float damageDone = attack.calculateDamage(Bulby, gengar, new Thunder());
        int roundedDamage = (int) Math.floor(damageDone);
        System.out.println("Damage dealt: " + roundedDamage);  */

        //scanner.close();



    }

    public static Trainer createTrainer(String name, long discordID, String discordUsername) {
        // This method can be used to create a trainer and set up their team
        // Create a Trainer and ask for their name
        TrainerCRUD trainerCRUDSearch = new TrainerCRUD();
        Trainer trainerSearch = trainerCRUDSearch.getTrainerByDiscordId(discordID);
        
        if (trainerSearch != null) {
            System.out.println("Welcome back, " + trainerSearch.getName() + "!");
            return trainerSearch;
        } else {
            System.out.println("No existing trainer found with Discord ID: " + discordID);
            System.out.println("Creating a new trainer profile for " + name + "...");
        
        
            Trainer trainer = new Trainer(name);

            TrainerCRUD trainerCRUDCreate = new TrainerCRUD();
            int trainerId = trainerCRUDCreate.createDBTrainer(discordID, discordUsername, name);
            trainer.setId(trainerId);

            System.out.println(trainer.getName() + ", welcome to the world of Pokemon!");

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("You can have up to 6 Pokemon on your team. Let's start by choosing your first Pokemon!");

            return trainer;
        }
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

    /**
     * Present a Pokémon's eligible learnset and let the player pick a move
     * to learn.  All I/O stays here in the controller; the domain classes
     * (Pokemon, LearnsetEntry) remain I/O-free.
     */
    public static void teachMoveFromLearnset(Pokemon p) {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available. Cannot teach move.");
            return;
        }
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(p);

        if (eligible.isEmpty()) {
            System.out.println(p.getName() + " has no new moves to learn right now.");
            return;
        }

        System.out.println("Pick a move for " + p.getName() + " to learn:");
        for (int i = 0; i < eligible.size(); i++) {
            LearnsetEntry e = eligible.get(i);
            System.out.printf("  %d: %s (%s %d)%n",
                    i + 1,
                    e.getMove().getMoveName(),
                    e.getSource(), e.getParameter());
        }

        System.out.print("Choice (1-" + eligible.size() + "): ");
        int choice = Integer.parseInt(console.readLine());
        if (choice < 1 || choice > eligible.size()) {
            System.out.println("Invalid choice. No move learned.");
            return;
        }

        Move picked = eligible.get(choice - 1).getMove();

        // If the moveset isn't full, just add it directly
        if (p.addMove(picked)) {
            System.out.println(p.getName() + " learned " + picked.getMoveName() + "!");
            return;
        }

        // Moveset is full — ask which move to replace
        System.out.println(p.getName() + " already knows 4 moves.");
        System.out.println("Replace a move with " + picked.getMoveName() + "? (yes/no)");
        String response = console.readLine();
        if (!response.equalsIgnoreCase("yes")) {
            System.out.println(p.getName() + " did not learn " + picked.getMoveName() + ".");
            return;
        }

        System.out.println("Which move should be forgotten?");
        for (int i = 0; i < p.getMoveset().size(); i++) {
            System.out.println("  " + (i + 1) + ": " + p.getMoveset().get(i).getMove().getMoveName());
        }
        System.out.print("Choice (1-4): ");
        int slot = Integer.parseInt(console.readLine());
        if (p.replaceMove(slot - 1, picked)) {
            System.out.println(p.getName() + " forgot a move and learned " + picked.getMoveName() + "!");
        } else {
            System.out.println("Invalid slot. Move not learned.");
        }
    }

    
    
}
