package pokemonGame;
import java.util.List;
import java.util.Scanner;
import pokemonGame.mons.*;

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
        // --- Interactive move teaching (all I/O lives here in the controller) ---
        teachMoveFromLearnset(bulbyTest);
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

    /**
     * Present a Pokémon's eligible learnset and let the player pick a move
     * to learn.  All I/O stays here in the controller; the domain classes
     * (Pokemon, LearnsetEntry) remain I/O-free.
     */
    public static void teachMoveFromLearnset(Pokemon p) {
        Scanner scanner = new Scanner(System.in);
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
        int choice = Integer.parseInt(scanner.nextLine());
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
        String response = scanner.nextLine();
        if (!response.equalsIgnoreCase("yes")) {
            System.out.println(p.getName() + " did not learn " + picked.getMoveName() + ".");
            return;
        }

        System.out.println("Which move should be forgotten?");
        for (int i = 0; i < p.getMoveset().size(); i++) {
            System.out.println("  " + (i + 1) + ": " + p.getMoveset().get(i).getMove().getMoveName());
        }
        System.out.print("Choice (1-4): ");
        int slot = Integer.parseInt(scanner.nextLine());
        if (p.replaceMove(slot - 1, picked)) {
            System.out.println(p.getName() + " forgot a move and learned " + picked.getMoveName() + "!");
        } else {
            System.out.println("Invalid slot. Move not learned.");
        }
    }

    
    // This method can be used to create a new Pokémon instance based on user input for species, level, and other attributes.  
    // It can return the newly created Pokémon instance.
    public static Pokemon createPokemon(String species, String name) {
        
        
        Pokemon createdMon = null;
        if (species == null) {
            System.out.println("No species selected. Please choose a valid Pokémon species.");
            return null;
        }

        switch (species.toLowerCase()) {
            case "bulbasaur": createdMon = new Bulbasaur(name); break;
            case "ivysaur": createdMon = new Ivysaur(name); break;
            case "venusaur": createdMon = new Venusaur(name); break;
            case "charmander": createdMon = new Charmander(name); break;
            case "charmeleon": createdMon = new Charmeleon(name); break;
            case "charizard": createdMon = new Charizard(name); break;
            case "squirtle": createdMon = new Squirtle(name); break;
            case "wartortle": createdMon = new Wartortle(name); break;
            case "blastoise": createdMon = new Blastoise(name); break;
            case "caterpie": createdMon = new Caterpie(name); break;
            case "metapod": createdMon = new Metapod(name); break;
            case "butterfree": createdMon = new Butterfree(name); break;
            case "weedle": createdMon = new Weedle(name); break;
            case "kakuna": createdMon = new Kakuna(name); break;
            case "beedrill": createdMon = new Beedrill(name); break;
            case "pidgey": createdMon = new Pidgey(name); break;
            case "pidgeotto": createdMon = new Pidgeotto(name); break;
            case "pidgeot": createdMon = new Pidgeot(name); break;
            case "rattata": createdMon = new Rattata(name); break;
            case "raticate": createdMon = new Raticate(name); break;
            case "spearow": createdMon = new Spearow(name); break;
            case "fearow": createdMon = new Fearow(name); break;
            case "ekans": createdMon = new Ekans(name); break;
            case "arbok": createdMon = new Arbok(name); break;
            case "pikachu": createdMon = new Pikachu(name); break;
            case "raichu": createdMon = new Raichu(name); break;
            case "sandshrew": createdMon = new Sandshrew(name); break;
            case "sandslash": createdMon = new Sandslash(name); break;
            case "nidoran f": case "nidoranf": createdMon = new NidoranF(name); break;
            case "nidorina": createdMon = new Nidorina(name); break;
            case "nidoqueen": createdMon = new Nidoqueen(name); break;
            case "nidoran m": case "nidoranm": createdMon = new NidoranM(name); break;
            case "nidorino": createdMon = new Nidorino(name); break;
            case "nidoking": createdMon = new Nidoking(name); break;
            case "clefairy": createdMon = new Clefairy(name); break;
            case "clefable": createdMon = new Clefable(name); break;
            case "vulpix": createdMon = new Vulpix(name); break;
            case "ninetales": createdMon = new Ninetales(name); break;
            case "jigglypuff": createdMon = new Jigglypuff(name); break;
            case "wigglytuff": createdMon = new Wigglytuff(name); break;
            case "zubat": createdMon = new Zubat(name); break;
            case "golbat": createdMon = new Golbat(name); break;
            case "oddish": createdMon = new Oddish(name); break;
            case "gloom": createdMon = new Gloom(name); break;
            case "vileplume": createdMon = new Vileplume(name); break;
            case "paras": createdMon = new Paras(name); break;
            case "parasect": createdMon = new Parasect(name); break;
            case "venonat": createdMon = new Venonat(name); break;
            case "venomoth": createdMon = new Venomoth(name); break;
            case "diglett": createdMon = new Diglett(name); break;
            case "dugtrio": createdMon = new Dugtrio(name); break;
            case "meowth": createdMon = new Meowth(name); break;
            case "persian": createdMon = new Persian(name); break;
            case "psyduck": createdMon = new Psyduck(name); break;
            case "golduck": createdMon = new Golduck(name); break;
            case "mankey": createdMon = new Mankey(name); break;
            case "primeape": createdMon = new Primeape(name); break;
            case "growlithe": createdMon = new Growlithe(name); break;
            case "arcanine": createdMon = new Arcanine(name); break;
            case "poliwag": createdMon = new Poliwag(name); break;
            case "poliwhirl": createdMon = new Poliwhirl(name); break;
            case "poliwrath": createdMon = new Poliwrath(name); break;
            case "abra": createdMon = new Abra(name); break;
            case "kadabra": createdMon = new Kadabra(name); break;
            case "alakazam": createdMon = new Alakazam(name); break;
            case "machop": createdMon = new Machop(name); break;
            case "machoke": createdMon = new Machoke(name); break;
            case "machamp": createdMon = new Machamp(name); break;
            case "bellsprout": createdMon = new Bellsprout(name); break;
            case "weepinbell": createdMon = new Weepinbell(name); break;
            case "victreebel": createdMon = new Victreebel(name); break;
            case "tentacool": createdMon = new Tentacool(name); break;
            case "tentacruel": createdMon = new Tentacruel(name); break;
            case "geodude": createdMon = new Geodude(name); break;
            case "graveler": createdMon = new Graveler(name); break;
            case "golem": createdMon = new Golem(name); break;
            case "ponyta": createdMon = new Ponyta(name); break;
            case "rapidash": createdMon = new Rapidash(name); break;
            case "slowpoke": createdMon = new Slowpoke(name); break;
            case "slowbro": createdMon = new Slowbro(name); break;
            case "magnemite": createdMon = new Magnemite(name); break;
            case "magneton": createdMon = new Magneton(name); break;
            case "farfetch'd": case "farfetchd": createdMon = new Farfetchd(name); break;
            case "doduo": createdMon = new Doduo(name); break;
            case "dodrio": createdMon = new Dodrio(name); break;
            case "seel": createdMon = new Seel(name); break;
            case "dewgong": createdMon = new Dewgong(name); break;
            case "grimer": createdMon = new Grimer(name); break;
            case "muk": createdMon = new Muk(name); break;
            case "shellder": createdMon = new Shellder(name); break;
            case "cloyster": createdMon = new Cloyster(name); break;
            case "gastly": createdMon = new Gastly(name); break;
            case "haunter": createdMon = new Haunter(name); break;
            case "gengar": createdMon = new Gengar(name); break;
            case "onix": createdMon = new Onix(name); break;
            case "drowzee": createdMon = new Drowzee(name); break;
            case "hypno": createdMon = new Hypno(name); break;
            case "krabby": createdMon = new Krabby(name); break;
            case "kingler": createdMon = new Kingler(name); break;
            case "voltorb": createdMon = new Voltorb(name); break;
            case "electrode": createdMon = new Electrode(name); break;
            case "exeggcute": createdMon = new Exeggcute(name); break;
            case "exeggutor": createdMon = new Exeggutor(name); break;
            case "cubone": createdMon = new Cubone(name); break;
            case "marowak": createdMon = new Marowak(name); break;
            case "hitmonlee": createdMon = new Hitmonlee(name); break;
            case "hitmonchan": createdMon = new Hitmonchan(name); break;
            case "lickitung": createdMon = new Lickitung(name); break;
            case "koffing": createdMon = new Koffing(name); break;
            case "weezing": createdMon = new Weezing(name); break;
            case "rhyhorn": createdMon = new Rhyhorn(name); break;
            case "rhydon": createdMon = new Rhydon(name); break;
            case "chansey": createdMon = new Chansey(name); break;
            case "tangela": createdMon = new Tangela(name); break;
            case "kangaskhan": createdMon = new Kangaskhan(name); break;
            case "horsea": createdMon = new Horsea(name); break;
            case "seadra": createdMon = new Seadra(name); break;
            case "goldeen": createdMon = new Goldeen(name); break;
            case "seaking": createdMon = new Seaking(name); break;
            case "staryu": createdMon = new Staryu(name); break;
            case "starmie": createdMon = new Starmie(name); break;
            case "mr. mime": case "mrmime": createdMon = new MrMime(name); break;
            case "scyther": createdMon = new Scyther(name); break;
            case "jynx": createdMon = new Jynx(name); break;
            case "electabuzz": createdMon = new Electabuzz(name); break;
            case "magmar": createdMon = new Magmar(name); break;
            case "pinsir": createdMon = new Pinsir(name); break;
            case "tauros": createdMon = new Tauros(name); break;
            case "magikarp": createdMon = new Magikarp(name); break;
            case "gyarados": createdMon = new Gyarados(name); break;
            case "lapras": createdMon = new Lapras(name); break;
            case "ditto": createdMon = new Ditto(name); break;
            case "eevee": createdMon = new Eevee(name); break;
            case "vaporeon": createdMon = new Vaporeon(name); break;
            case "jolteon": createdMon = new Jolteon(name); break;
            case "flareon": createdMon = new Flareon(name); break;
            case "porygon": createdMon = new Porygon(name); break;
            case "omanyte": createdMon = new Omanyte(name); break;
            case "omastar": createdMon = new Omastar(name); break;
            case "kabuto": createdMon = new Kabuto(name); break;
            case "kabutops": createdMon = new Kabutops(name); break;
            case "aerodactyl": createdMon = new Aerodactyl(name); break;
            case "snorlax": createdMon = new Snorlax(name); break;
            case "articuno": createdMon = new Articuno(name); break;
            case "zapdos": createdMon = new Zapdos(name); break;
            case "moltres": createdMon = new Moltres(name); break;
            case "dratini": createdMon = new Dratini(name); break;
            case "dragonair": createdMon = new Dragonair(name); break;
            case "dragonite": createdMon = new Dragonite(name); break;
            case "mewtwo": createdMon = new Mewtwo(name); break;
            case "mew": createdMon = new Mew(name); break;
                
            default:
                System.out.println("Species not recognized. Please choose a valid Pokémon species.");
                return null;
        }
               
        return createdMon;
    }
}
