package pokemonGame.species;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.model.Pokemon;

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

public class PokemonFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonFactory.class);
    

    // ============================
    // ===   REGISTRY FACTORY   ===
    // ============================

    

    public static Pokemon createPokemonFromRegistry(PokeSpecies species, String nickname) {
        if (species == null) {
            LOGGER.warn("Species not recognized. Please choose a valid Pokemon species.");
            return null;
        }
        try {
            return species.createPokemon(nickname);
        } catch (Exception e) {
            LOGGER.error("Failed to create Pokemon instance for species: {}", species.getDisplayName(), e);
            return null;
        }

    }

    
}
