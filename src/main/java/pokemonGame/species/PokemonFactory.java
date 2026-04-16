package pokemonGame.species;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.model.Pokemon;

import java.util.Collections;
import java.util.Set;

/**
 * Factory for creating {@link Pokemon} instances from species names or
 * {@link PokeSpecies} enum values. Maintains a static registry mapping
 * species display names to constructor references.
 *
 * <p>Usage: {@code PokemonFactory.createPokemonFromRegistry(PokeSpecies.BULBASAUR, "Bulby")}</p>
 *
 * @see PokeSpecies
 * @see Pokemon
 */
// TODO: SPC-2 — This registry duplicates PokeSpecies lookup. Consolidate into one canonical lookup path.
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
                            // TODO: SPC-4 — Change to LOGGER.debug(). INFO on every creation floods logs in production.
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
                    
                    // TODO: SPC-4 — Change to LOGGER.debug(). INFO on every creation floods logs in production.
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
