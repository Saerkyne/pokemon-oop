package pokemonGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Function;
import pokemonGame.mons.*;



public enum PokeSpecies {
    ABRA("Abra", "Abra", Abra::new),
    AERODACTYL("Aerodactyl", "Aerodactyl", Aerodactyl::new),
    ALAKAZAM("Alakazam", "Alakazam", Alakazam::new),
    ARBOK("Arbok", "Arbok", Arbok::new),
    ARCANINE("Arcanine", "Arcanine", Arcanine::new),
    ARTICUNO("Articuno", "Articuno", Articuno::new),
    BEEDRILL("Beedrill", "Beedrill", Beedrill::new),
    BELLSPROUT("Bellsprout", "Bellsprout", Bellsprout::new),
    BLASTOISE("Blastoise", "Blastoise", Blastoise::new),
    BULBASAUR("Bulbasaur", "Bulbasaur", Bulbasaur::new),
    BUTTERFREE("Butterfree", "Butterfree", Butterfree::new),
    CATERPIE("Caterpie", "Caterpie", Caterpie::new),
    CHANSEY("Chansey", "Chansey", Chansey::new),
    CHARIZARD("Charizard", "Charizard", Charizard::new),
    CHARMANDER("Charmander", "Charmander", Charmander::new),
    CHARMELEON("Charmeleon", "Charmeleon", Charmeleon::new),
    CLEFABLE("Clefable", "Clefable", Clefable::new),
    CLEFAIRY("Clefairy", "Clefairy", Clefairy::new),
    CLOYSTER("Cloyster", "Cloyster", Cloyster::new),
    CUBONE("Cubone", "Cubone", Cubone::new),
    DEWGONG("Dewgong", "Dewgong", Dewgong::new),
    DIGLETT("Diglett", "Diglett", Diglett::new),
    DITTO("Ditto", "Ditto", Ditto::new),
    DODRIO("Dodrio", "Dodrio", Dodrio::new),
    DODUO("Doduo", "Doduo", Doduo::new),
    DRAGONAIR("Dragonair", "Dragonair", Dragonair::new),
    DRAGONITE("Dragonite", "Dragonite", Dragonite::new),
    DRATINI("Dratini", "Dratini", Dratini::new),
    DROWZEE("Drowzee", "Drowzee", Drowzee::new),
    DUGTRIO("Dugtrio", "Dugtrio", Dugtrio::new),
    EEVEE("Eevee", "Eevee", Eevee::new),
    EKANS("Ekans", "Ekans", Ekans::new),
    ELECTABUZZ("Electabuzz", "Electabuzz", Electabuzz::new),
    ELECTRODE("Electrode", "Electrode", Electrode::new),
    EXEGGCUTE("Exeggcute", "Exeggcute", Exeggcute::new),
    EXEGGUTOR("Exeggutor", "Exeggutor", Exeggutor::new),
    FARFETCHD("Farfetch'd", "Farfetchd", Farfetchd::new, new String[]{"Farfetch", "Farfetchd", "Farfetched"}),
    FEAROW("Fearow", "Fearow", Fearow::new),
    FLAREON("Flareon", "Flareon", Flareon::new),
    GASTLY("Gastly", "Gastly", Gastly::new),
    GENGAR("Gengar", "Gengar", Gengar::new),
    GEODUDE("Geodude", "Geodude", Geodude::new),
    GLOOM("Gloom", "Gloom", Gloom::new),
    GOLBAT("Golbat", "Golbat", Golbat::new),
    GOLDEEN("Goldeen", "Goldeen", Goldeen::new),
    GOLDUCK("Golduck", "Golduck", Golduck::new),
    GOLEM("Golem", "Golem", Golem::new),
    GRAVELER("Graveler", "Graveler", Graveler::new),
    GRIMER("Grimer", "Grimer", Grimer::new),
    GROWLITHE("Growlithe", "Growlithe", Growlithe::new),
    GYARADOS("Gyarados", "Gyarados", Gyarados::new),
    HAUNTER("Haunter", "Haunter", Haunter::new),
    HITMONCHAN("Hitmonchan", "Hitmonchan", Hitmonchan::new),
    HITMONLEE("Hitmonlee", "Hitmonlee", Hitmonlee::new),
    HORSEA("Horsea", "Horsea", Horsea::new),
    HYPNO("Hypno", "Hypno", Hypno::new),
    IVYSAUR("Ivysaur", "Ivysaur", Ivysaur::new),
    JIGGLYPUFF("Jigglypuff", "Jigglypuff", Jigglypuff::new),
    JOLTEON("Jolteon", "Jolteon", Jolteon::new),
    JYNX("Jynx", "Jynx", Jynx::new),
    KABUTO("Kabuto", "Kabuto", Kabuto::new),
    KABUTOPS("Kabutops", "Kabutops", Kabutops::new),
    KADABRA("Kadabra", "Kadabra", Kadabra::new),
    KAKUNA("Kakuna", "Kakuna", Kakuna::new),
    KANGASKHAN("Kangaskhan", "Kangaskhan", Kangaskhan::new),
    KINGLER("Kingler", "Kingler", Kingler::new),
    KOFFING("Koffing", "Koffing", Koffing::new),
    KRABBY("Krabby", "Krabby", Krabby::new),
    LAPRAS("Lapras", "Lapras", Lapras::new),
    LICKITUNG("Lickitung", "Lickitung", Lickitung::new),
    MACHAMP("Machamp", "Machamp", Machamp::new),
    MACHOKE("Machoke", "Machoke", Machoke::new),
    MACHOP("Machop", "Machop", Machop::new),
    MAGIKARP("Magikarp", "Magikarp", Magikarp::new),
    MAGMAR("Magmar", "Magmar", Magmar::new),
    MAGNEMITE("Magnemite", "Magnemite", Magnemite::new),
    MAGNETON("Magneton", "Magneton", Magneton::new),
    MANKEY("Mankey", "Mankey", Mankey::new),
    MAROWAK("Marowak", "Marowak", Marowak::new),
    MEOWTH("Meowth", "Meowth", Meowth::new),
    METAPOD("Metapod", "Metapod", Metapod::new),
    MEW("Mew", "Mew", Mew::new),
    MEWTWO("Mewtwo", "Mewtwo", Mewtwo::new),
    MOLTRES("Moltres", "Moltres", Moltres::new),
    MRMIME("Mr. Mime", "MrMime", MrMime::new, new String[]{"Mrmime", "Mr. Mime", "Mr Mime"}),
    MUK("Muk", "Muk", Muk::new),
    NIDOKING("Nidoking", "Nidoking", Nidoking::new),
    NIDOQUEEN("Nidoqueen", "Nidoqueen", Nidoqueen::new),
    NIDORANF("Nidoran♀", "NidoranF", NidoranF::new, new String[]{"nidoranf", "nidoran f", "f nidoran", "fnidoran"}),
    NIDORANM("Nidoran♂", "NidoranM", NidoranM::new, new String[]{"nidoranm", "nidoran m", "m nidoran", "mnidoran"}),
    NIDORINA("Nidorina", "Nidorina", Nidorina::new),
    NIDORINO("Nidorino", "Nidorino", Nidorino::new),
    NINETALES("Ninetales", "Ninetales", Ninetales::new),
    ODDISH("Oddish", "Oddish", Oddish::new),
    OMANYTE("Omanyte", "Omanyte", Omanyte::new),
    OMASTAR("Omastar", "Omastar", Omastar::new),
    ONIX("Onix", "Onix", Onix::new),
    PARAS("Paras", "Paras", Paras::new),
    PARASECT("Parasect", "Parasect", Parasect::new),
    PERSIAN("Persian", "Persian", Persian::new),
    PIDGEOT("Pidgeot", "Pidgeot", Pidgeot::new),
    PIDGEOTTO("Pidgeotto", "Pidgeotto", Pidgeotto::new),
    PIDGEY("Pidgey", "Pidgey", Pidgey::new),
    PIKACHU("Pikachu", "Pikachu", Pikachu::new),
    PINSIR("Pinsir", "Pinsir", Pinsir::new),
    POLIWAG("Poliwag", "Poliwag", Poliwag::new),
    POLIWHIRL("Poliwhirl", "Poliwhirl", Poliwhirl::new),
    POLIWRATH("Poliwrath", "Poliwrath", Poliwrath::new),
    PONYTA("Ponyta", "Ponyta", Ponyta::new),
    PORYGON("Porygon", "Porygon", Porygon::new),
    PRIMEAPE("Primeape", "Primeape", Primeape::new),
    PSYDUCK("Psyduck", "Psyduck", Psyduck::new),
    RAICHU("Raichu", "Raichu", Raichu::new),
    RAPIDASH("Rapidash", "Rapidash", Rapidash::new),
    RATICATE("Raticate", "Raticate", Raticate::new),
    RATTATA("Rattata", "Rattata", Rattata::new),
    RHYDON("Rhydon", "Rhydon", Rhydon::new),
    RHYHORN("Rhyhorn", "Rhyhorn", Rhyhorn::new),
    SANDSHREW("Sandshrew", "Sandshrew", Sandshrew::new),
    SANDSLASH("Sandslash", "Sandslash", Sandslash::new),
    SCYTHER("Scyther", "Scyther", Scyther::new),
    SEADRA("Seadra", "Seadra", Seadra::new),
    SEAKING("Seaking", "Seaking", Seaking::new),
    SEEL("Seel", "Seel", Seel::new),
    SHELLDER("Shellder", "Shellder", Shellder::new),
    SLOWBRO("Slowbro", "Slowbro", Slowbro::new),
    SLOWPOKE("Slowpoke", "Slowpoke", Slowpoke::new),
    SNORLAX("Snorlax", "Snorlax", Snorlax::new),
    SPEAROW("Spearow", "Spearow", Spearow::new),
    SQUIRTLE("Squirtle", "Squirtle", Squirtle::new),
    STARMIE("Starmie", "Starmie", Starmie::new),
    STARYU("Staryu", "Staryu", Staryu::new),
    TANGELA("Tangela", "Tangela", Tangela::new),
    TAUROS("Tauros", "Tauros", Tauros::new),
    TENTACOOL("Tentacool", "Tentacool", Tentacool::new),
    TENTACRUEL("Tentacruel", "Tentacruel", Tentacruel::new),
    VAPOREON("Vaporeon", "Vaporeon", Vaporeon::new),
    VENOMOTH("Venomoth", "Venomoth", Venomoth::new),
    VENONAT("Venonat", "Venonat", Venonat::new),
    VENUSAUR("Venusaur", "Venusaur", Venusaur::new),
    VICTREEBEL("Victreebel", "Victreebel", Victreebel::new),
    VILEPLUME("Vileplume", "Vileplume", Vileplume::new),
    VOLTORB("Voltorb", "Voltorb", Voltorb::new),
    VULPIX("Vulpix", "Vulpix", Vulpix::new),
    WARTORTLE("Wartortle", "Wartortle", Wartortle::new),
    WEEDLE("Weedle", "Weedle", Weedle::new),
    WEEPINBELL("Weepinbell", "Weepinbell", Weepinbell::new),
    WEEZING("Weezing", "Weezing", Weezing::new),
    WIGGLYTUFF("Wigglytuff", "Wigglytuff", Wigglytuff::new),
    ZAPDOS("Zapdos", "Zapdos", Zapdos::new),
    ZUBAT("Zubat", "Zubat", Zubat::new);

    private final String displayName;
    private final String[] aliases;
    private final String className;
    private final Function<String, Pokemon> constructor;
    private static final Logger LOGGER = LoggerFactory.getLogger(PokeSpecies.class);
        
    PokeSpecies(String displayName, String className, Function<String, Pokemon> constructor, String[] aliases) {
        this.displayName = displayName;
        this.aliases = aliases;
        this.className = className;
        this.constructor = constructor;
    }

    // Convenience constructor for species without aliases or class passthrough, we can discard the aliases parameter and just pass the display name.
    PokeSpecies(String displayName, String className, Function<String, Pokemon> constructor) {
        this(displayName, className, constructor, new String[0]);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String[] getAliases() {
        return aliases.clone();
    }
    
    public String getClassName() {
        return className;
    }

    public Pokemon createPokemon(String nickname) {
        return constructor.apply(nickname);
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
