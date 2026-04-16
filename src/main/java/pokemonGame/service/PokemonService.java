package pokemonGame.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Trainer;
import java.sql.ResultSet;
import java.sql.SQLException;

import pokemonGame.db.PokemonCRUD;

public class PokemonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonService.class);
    private final PokemonCRUD pokemonCRUD;

    public PokemonService(PokemonCRUD pokemonCRUD) {
        this.pokemonCRUD = pokemonCRUD;
        // Initialize any necessary resources or dependencies here
    }

    public Pokemon mapDbPokemonToPokemon(ResultSet rs, Trainer trainer) throws SQLException {
        LOGGER.info("Mapping Pokemon from database for trainer ID {}: instance_id={}, species={}, level={}", 
                trainer.getTrainerDbId(), rs.getInt("instance_id"), rs.getString("species"), rs.getInt("level"));
        return pokemonCRUD.mapResultSetToPokemon(rs, trainer);
    }
}
