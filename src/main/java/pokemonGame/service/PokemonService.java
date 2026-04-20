package pokemonGame.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.model.Move;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Trainer;
import pokemonGame.moves.PokeMove;
import pokemonGame.species.PokeSpecies;
import pokemonGame.species.PokemonFactory;

import java.util.List;

import pokemonGame.core.Natures;
import pokemonGame.db.PokemonCRUD;
import pokemonGame.core.EvManager;
import pokemonGame.core.Stat;
import pokemonGame.core.StatCalculator;

public class PokemonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonService.class);
    private final MoveSlotService moveSlotService;

    public PokemonService(PokemonCRUD pokemonCRUD, MoveSlotService moveSlotService) {
        this.moveSlotService = moveSlotService;
    }

    // TODO(review 2026-04-20): Validate pokemonData size/nulls before positional reads.
    // Empty or partially populated DAO rows currently become IndexOutOfBoundsException/NullPointerException deep inside rehydration.
    public Pokemon mapDbPokemonToObject(List<Object> pokemonData, Trainer trainer) {
        LOGGER.debug("Mapping Pokemon from database for trainer ID {}: instance_id={}, species={}, level={}",
                trainer.getTrainerDbId(), pokemonData.get(0), pokemonData.get(2), pokemonData.get(4));
        PokeSpecies species = PokeSpecies.valueOf((String) pokemonData.get(2));
        String nickname = (String) pokemonData.get(3);
        Pokemon dbMon = PokemonFactory.createPokemonFromRegistry(species, nickname);


        if (pokemonData.size() >= 21) {
            dbMon.setPokemonDbId((int) pokemonData.get(0));
            dbMon.setTrainer(trainer);
            dbMon.setLevel((int) pokemonData.get(4));
            dbMon.setNature((Natures.valueOf((String) pokemonData.get(5))));
            dbMon.setIvHp((int) pokemonData.get(6));
            dbMon.setIvAttack((int) pokemonData.get(7));
            dbMon.setIvDefense((int) pokemonData.get(8));
            dbMon.setIvSpecialAttack((int) pokemonData.get(9));
            dbMon.setIvSpecialDefense((int) pokemonData.get(10));
            dbMon.setIvSpeed((int) pokemonData.get(11));
            EvManager.setEv(dbMon, Stat.HP, (int) pokemonData.get(12));
            EvManager.setEv(dbMon, Stat.ATTACK, (int) pokemonData.get(13));
            EvManager.setEv(dbMon, Stat.DEFENSE, (int) pokemonData.get(14));
            EvManager.setEv(dbMon, Stat.SPECIAL_ATTACK, (int) pokemonData.get(15));
            EvManager.setEv(dbMon, Stat.SPECIAL_DEFENSE, (int) pokemonData.get(16));
            EvManager.setEv(dbMon, Stat.SPEED, (int) pokemonData.get(17));
            dbMon.setCurrentExp((int) pokemonData.get(18));
            StatCalculator.calculateAllStats(dbMon);
            dbMon.setCurrentHP((int) pokemonData.get(19));
        }

        // Load moves from pokemon_movesets and populate the in-memory moveset
        List<String[]> moveRows = moveSlotService.getCurrentDbMoves(dbMon.getPokemonDbId());
        for (String[] row : moveRows) {
            String moveName = row[0];
            int pp = Integer.parseInt(row[1]);
            try {
                Move move = PokeMove.fromString(moveName).getMoveInstance();
                dbMon.addMove(move);
                // Set the persisted PP (may differ from max if the move was partially used)
                dbMon.getMoveSet().get(dbMon.getMoveSet().size() - 1).setCurrentPP(pp);
            } catch (IllegalArgumentException e) {
                LOGGER.error("Unknown move '{}' in DB for Pokémon instance {}; skipping.", moveName, dbMon.getPokemonDbId());
            }
        }

        return dbMon;
    }
}
