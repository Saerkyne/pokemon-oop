package pokemonGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public enum PokeSpecies {
    ABRA("Abra", "Abra"),
    AERODACTYL("Aerodactyl", "Aerodactyl"),
    ALAKAZAM("Alakazam", "Alakazam"),
    ARBOK("Arbok", "Arbok"),
    ARCANINE("Arcanine", "Arcanine"),
    ARTICUNO("Articuno", "Articuno"),
    BEEDRILL("Beedrill", "Beedrill"),
    BELLSPROUT("Bellsprout", "Bellsprout"),
    BLASTOISE("Blastoise", "Blastoise"),
    BULBASAUR("Bulbasaur", "Bulbasaur"),
    BUTTERFREE("Butterfree", "Butterfree"),
    CATERPIE("Caterpie", "Caterpie"),
    CHANSEY("Chansey", "Chansey"),
    CHARIZARD("Charizard", "Charizard"),
    CHARMANDER("Charmander", "Charmander"),
    CHARMELEON("Charmeleon", "Charmeleon"),
    CLEFABLE("Clefable", "Clefable"),
    CLEFAIRY("Clefairy", "Clefairy"),
    CLOYSTER("Cloyster", "Cloyster"),
    CUBONE("Cubone", "Cubone"),
    DEWGONG("Dewgong", "Dewgong"),
    DIGLETT("Diglett", "Diglett"),
    DITTO("Ditto", "Ditto"),
    DODRIO("Dodrio", "Dodrio"),
    DODUO("Doduo", "Doduo"),
    DRAGONAIR("Dragonair", "Dragonair"),
    DRAGONITE("Dragonite", "Dragonite"),
    DRATINI("Dratini", "Dratini"),
    DROWZEE("Drowzee", "Drowzee"),
    DUGTRIO("Dugtrio", "Dugtrio"),
    EEVEE("Eevee", "Eevee"),
    EKANS("Ekans", "Ekans"),
    ELECTABUZZ("Electabuzz", "Electabuzz"),
    ELECTRODE("Electrode", "Electrode"),
    EXEGGCUTE("Exeggcute", "Exeggcute"),
    EXEGGUTOR("Exeggutor", "Exeggutor"),
    FARFETCHD("Farfetch'd", "Farfetchd", new String[]{"Farfetch", "Farfetchd", "Farfetched"}),
    FEAROW("Fearow", "Fearow"),
    FLAREON("Flareon", "Flareon"),
    GASTLY("Gastly", "Gastly"),
    GENGAR("Gengar", "Gengar"),
    GEODUDE("Geodude", "Geodude"),
    GLOOM("Gloom", "Gloom"),
    GOLBAT("Golbat", "Golbat"),
    GOLDEEN("Goldeen", "Goldeen"),
    GOLDUCK("Golduck", "Golduck"),
    GOLEM("Golem", "Golem"),
    GRAVELER("Graveler", "Graveler"),
    GRIMER("Grimer", "Grimer"),
    GROWLITHE("Growlithe", "Growlithe"),
    GYARADOS("Gyarados", "Gyarados"),
    HAUNTER("Haunter", "Haunter"),
    HITMONCHAN("Hitmonchan", "Hitmonchan"),
    HITMONLEE("Hitmonlee", "Hitmonlee"),
    HORSEA("Horsea", "Horsea"),
    HYPNO("Hypno", "Hypno"),
    IVYSAUR("Ivysaur", "Ivysaur"),
    JIGGLYPUFF("Jigglypuff", "Jigglypuff"),
    JOLTEON("Jolteon", "Jolteon"),
    JYNX("Jynx", "Jynx"),
    KABUTO("Kabuto", "Kabuto"),
    KABUTOPS("Kabutops", "Kabutops"),
    KADABRA("Kadabra", "Kadabra"),
    KAKUNA("Kakuna", "Kakuna"),
    KANGASKHAN("Kangaskhan", "Kangaskhan"),
    KINGLER("Kingler", "Kingler"),
    KOFFING("Koffing", "Koffing"),
    KRABBY("Krabby", "Krabby"),
    LAPRAS("Lapras", "Lapras"),
    LICKITUNG("Lickitung", "Lickitung"),
    MACHAMP("Machamp", "Machamp"),
    MACHOKE("Machoke", "Machoke"),
    MACHOP("Machop", "Machop"),
    MAGIKARP("Magikarp", "Magikarp"),
    MAGMAR("Magmar", "Magmar"),
    MAGNEMITE("Magnemite", "Magnemite"),
    MAGNETON("Magneton", "Magneton"),
    MANKEY("Mankey", "Mankey"),
    MAROWAK("Marowak", "Marowak"),
    MEOWTH("Meowth", "Meowth"),
    METAPOD("Metapod", "Metapod"),
    MEW("Mew", "Mew"),
    MEWTWO("Mewtwo", "Mewtwo"),
    MOLTRES("Moltres", "Moltres"),
    MRMIME("Mr. Mime", "Mr Mime", new String[]{"Mrmime", "Mr. Mime"}),
    MUK("Muk", "Muk"),
    NIDOKING("Nidoking", "Nidoking"),
    NIDOQUEEN("Nidoqueen", "Nidoqueen"),
    NIDORANF("Nidoran♀", "NidoranF", new String[]{"nidoranf", "nidoran f", "f nidoran", "fnidoran"}),
    NIDORANM("Nidoran♂", "NidoranM", new String[]{"nidoranm", "nidoran m", "m nidoran", "mnidoran"}),
    NIDORINA("Nidorina", "Nidorina"),
    NIDORINO("Nidorino", "Nidorino"),
    NINETALES("Ninetales", "Ninetales"),
    ODDISH("Oddish", "Oddish"),
    OMANYTE("Omanyte", "Omanyte"),
    OMASTAR("Omastar", "Omastar"),
    ONIX("Onix", "Onix"),
    PARAS("Paras", "Paras"),
    PARASECT("Parasect", "Parasect"),
    PERSIAN("Persian", "Persian"),
    PIDGEOT("Pidgeot", "Pidgeot"),
    PIDGEOTTO("Pidgeotto", "Pidgeotto"),
    PIDGEY("Pidgey", "Pidgey"),
    PIKACHU("Pikachu", "Pikachu"),
    PINSIR("Pinsir", "Pinsir"),
    POLIWAG("Poliwag", "Poliwag"),
    POLIWHIRL("Poliwhirl", "Poliwhirl"),
    POLIWRATH("Poliwrath", "Poliwrath"),
    PONYTA("Ponyta", "Ponyta"),
    PORYGON("Porygon", "Porygon"),
    PRIMEAPE("Primeape", "Primeape"),
    PSYDUCK("Psyduck", "Psyduck"),
    RAICHU("Raichu", "Raichu"),
    RAPIDASH("Rapidash", "Rapidash"),
    RATICATE("Raticate", "Raticate"),
    RATTATA("Rattata", "Rattata"),
    RHYDON("Rhydon", "Rhydon"),
    RHYHORN("Rhyhorn", "Rhyhorn"),
    SANDSHREW("Sandshrew", "Sandshrew"),
    SANDSLASH("Sandslash", "Sandslash"),
    SCYTHER("Scyther", "Scyther"),
    SEADRA("Seadra", "Seadra"),
    SEAKING("Seaking", "Seaking"),
    SEEL("Seel", "Seel"),
    SHELLDER("Shellder", "Shellder"),
    SLOWBRO("Slowbro", "Slowbro"),
    SLOWPOKE("Slowpoke", "Slowpoke"),
    SNORLAX("Snorlax", "Snorlax"),
    SPEAROW("Spearow", "Spearow"),
    SQUIRTLE("Squirtle", "Squirtle"),
    STARMIE("Starmie", "Starmie"),
    STARYU("Staryu", "Staryu"),
    TANGELA("Tangela", "Tangela"),
    TAUROS("Tauros", "Tauros"),
    TENTACOOL("Tentacool", "Tentacool"),
    TENTACRUEL("Tentacruel", "Tentacruel"),
    VAPOREON("Vaporeon", "Vaporeon"),
    VENOMOTH("Venomoth", "Venomoth"),
    VENONAT("Venonat", "Venonat"),
    VENUSAUR("Venusaur", "Venusaur"),
    VICTREEBEL("Victreebel", "Victreebel"),
    VILEPLUME("Vileplume", "Vileplume"),
    VOLTORB("Voltorb", "Voltorb"),
    VULPIX("Vulpix", "Vulpix"),
    WARTORTLE("Wartortle", "Wartortle"),
    WEEDLE("Weedle", "Weedle"),
    WEEPINBELL("Weepinbell", "Weepinbell"),
    WEEZING("Weezing", "Weezing"),
    WIGGLYTUFF("Wigglytuff", "Wigglytuff"),
    ZAPDOS("Zapdos", "Zapdos"),
    ZUBAT("Zubat", "Zubat");

    private final String displayName;
    private final String[] aliases;
    private final String className;
    private static final Logger LOGGER = LoggerFactory.getLogger(PokeSpecies.class);
        
    PokeSpecies(String displayName, String className, String[] aliases) {
        this.displayName = displayName;
        this.aliases = aliases;
        this.className = className;
    }

    // Convenience constructor for species without aliases or class passthrough, we can discard the aliases parameter and just pass the display name.
    PokeSpecies(String displayName, String className) {
        this(displayName, className, new String[0]);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String[] getAliases() {
        return aliases;
    }
    
    public String getClassName() {
        return className;
    }

    public static PokeSpecies getSpeciesByString(String input) {
        if (input == null) {
            LOGGER.warn("Input is null");
            return null;
        }
        LOGGER.info("Looking up species for input: '{}'", input);   
        String norm = input.trim().toLowerCase();
        for (PokeSpecies species : PokeSpecies.values()) {
            if (species.getDisplayName().toLowerCase().equals(norm)) {
                LOGGER.info("Matched input '{}' to species display name '{}'", input, species.getDisplayName());
                return species;
            }
            for (String alias : species.getAliases()) {
                if (alias.toLowerCase().equals(norm)) {
                    LOGGER.info("Matched input '{}' to species alias '{}'", input, alias);

                    return species;
                }
            }
        }
        LOGGER.warn("No match found for input '{}'", input);
        return null; // No match found
    }
    
}
