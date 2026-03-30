package pokemonGame;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.Set;

// NEEDS TO BE UPDATE TO USE ENUM INSTEAD OF STRING FOR SPECIES.
// NO LONGER NEEDS TO SCAN FOR CLASSES, JUST MAP ENUM VALUES TO CONSTRUCTORS IN A STATIC BLOCK.

public class PokemonFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonFactory.class);
    private static final Map<String, Function<String, Pokemon>> REGISTRY = new HashMap<>();

    static {

        // Registration using enum values instead of classpath scanning
        for (PokeSpecies species : PokeSpecies.values()) {
            String key = species.getDisplayName().toLowerCase().trim();
            String[] aliasKey = species.getAliases();

            if (aliasKey != null && aliasKey.length > 0) {
                for (String alias : aliasKey) {
                    REGISTRY.put(alias.toLowerCase().trim(), name -> {
                        try {
                            LOGGER.info("Created alias: {} for species: {}", alias, species.getDisplayName());
                            return species.createPokemon(name);
                        } catch (Exception e) {
                            LOGGER.error("Failed to create instance for alias: {} of species: {}", alias, species.getDisplayName(), e);
                            return null;
                        }
                    }); 
                        
                }
            }

            REGISTRY.put(key, name -> {
                try {
                    
                    LOGGER.info("Created species: {} with class: {}", species.getDisplayName(), species.getClassName());
                    return species.createPokemon(name);
                } catch (Exception e) {
                    LOGGER.error("Failed to create instance for species: {}", species.getDisplayName(), e);
                    return null;
                }
            });
        }


    }

    // ============================
    // ===   REGISTRY FACTORY   ===
    // ============================

    

    public static Pokemon createPokemonFromRegistry(PokeSpecies species, String nickname) {
        Function<String, Pokemon> constructor = REGISTRY.get(species.getDisplayName().toLowerCase());
        
        if (constructor == null) {
            LOGGER.warn("Species not recognized in registry. Please choose a valid Pokémon species.");
            return null;
        }
        return constructor.apply(nickname);

    }

    // Getter for all registered species names (for autocomplete)
    public static Set<String> getSpeciesNames() {
        return Collections.unmodifiableSet(REGISTRY.keySet());
    }
}
