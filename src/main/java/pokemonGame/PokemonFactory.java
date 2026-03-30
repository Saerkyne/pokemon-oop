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
        /* try (ScanResult scan = new ClassGraph()
                .acceptPackages("pokemonGame.mons")
                .enableClassInfo()
                .scan()) {

            for (ClassInfo cls : scan.getSubclasses(Pokemon.class)) {
                try {
                    Class<? extends Pokemon> species = cls.loadClass().asSubclass(Pokemon.class);
                    Constructor<? extends Pokemon> ctor = species.getConstructor(String.class);
                    String key = species.getSimpleName().toLowerCase();

                    REGISTRY.put(key, name -> {
                        try { return ctor.newInstance(name); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    });

                } catch (NoSuchMethodException e) {
                    // This species lacks a (String) constructor — skip it, don't abort the whole scan
                    LOGGER.warn("Skipping {} — no (String) constructor found.", cls.getSimpleName(), e);
                } catch (Exception e) {
                    LOGGER.warn("Skipping {} — unexpected error during registration.", cls.getSimpleName(), e);
                }
            }

            LOGGER.info("PokemonFactory registered {} species.", REGISTRY.size());
        } catch (Exception e) {
            LOGGER.error("ClassGraph scan failed — no species registered.", e);
        } */


        // Registration using enum values instead of classpath scanning
        for (PokeSpecies species : PokeSpecies.values()) {
            String key = species.getDisplayName().toLowerCase().trim();
            String[] aliasKey = species.getAliases();

            if (aliasKey != null && aliasKey.length > 0) {
                for (String alias : aliasKey) {
                    REGISTRY.put(alias.toLowerCase().trim(), name -> {
                        try {
                            LOGGER.info("Registered alias: {} for species: {}", alias, species.getDisplayName());
                            return species.createPokemon(name);
                        } catch (Exception e) {
                            LOGGER.error("Failed to create instance for alias: " + alias + " of species: " + species.getDisplayName(), e);
                            return null;
                        }
                    }); 
                        
                }
            }

            REGISTRY.put(key, name -> {
                try {
                    
                    LOGGER.info("Registered species: {} with class: {}", species.getDisplayName(), species.getClassName());
                    return species.createPokemon(name);
                } catch (Exception e) {
                    LOGGER.error("Failed to create instance for species: " + species.getDisplayName(), e);
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
