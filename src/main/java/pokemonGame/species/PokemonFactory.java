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
            // TODO(review 2026-04-20): Catch narrower construction failures or let them surface in startup/tests.
            // Swallowing every exception here turns broken species registry wiring into a generic null return that is harder to diagnose.
            LOGGER.error("Failed to create Pokemon instance for species: {}", species.getDisplayName(), e);
            return null;
        }

    }

    
}
