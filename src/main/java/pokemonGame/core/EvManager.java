package pokemonGame.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.model.Pokemon;

/**
 * Manages Effort Value (EV) operations for Pokémon. Handles adding, setting,
 * and querying EVs while enforcing the per-stat cap of 252 and the total cap
 * of 510 across all six stats.
 *
 * @see Stat
 * @see StatCalculator
 */
public class EvManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvManager.class);


    // Method for calculating how much EV can be added without exceeding caps for any EV
    private int evAddable(Pokemon pokemon, Stat stat, int newEvValue) {
        int currentEvValue = getEv(pokemon, stat);
        int currentEvTotal = getTotalEv(pokemon);
        int roomInStat = 252 - currentEvValue;
        int roomInTotal = 510 - currentEvTotal;
        int maxRoom = Math.min(roomInStat, roomInTotal);
        int cappedAdd = Math.min(newEvValue, maxRoom);
        int maximumAddableValue = Math.max(0, cappedAdd);
        return maximumAddableValue;
    }

    // Method for overwriting EV values, with capping logic to ensure no caps are exceeded
    private int evCapper(Pokemon pokemon, Stat stat, int newEvValue) {
        int currentEvValue = getEv(pokemon, stat);
        int currentEvTotal = getTotalEv(pokemon);
        int totalWithoutThisStat = currentEvTotal - currentEvValue;
        int roomInTotal = 510 - totalWithoutThisStat;
        int cappedValue = Math.min(newEvValue, Math.min(252, roomInTotal));
        int finalCappedValue = Math.max(0, cappedValue);
        return finalCappedValue;
    }


    public void setEv(Pokemon pokemon, Stat stat, int evValue) {
        switch (stat) {
            case HP -> pokemon.setEvHp(evCapper(pokemon, stat, evValue));
            case ATTACK -> pokemon.setEvAttack(evCapper(pokemon, stat, evValue));
            case DEFENSE -> pokemon.setEvDefense(evCapper(pokemon, stat, evValue));
            case SPECIAL_ATTACK -> pokemon.setEvSpecialAttack(evCapper(pokemon, stat, evValue));
            case SPECIAL_DEFENSE -> pokemon.setEvSpecialDefense(evCapper(pokemon, stat, evValue));
            case SPEED -> pokemon.setEvSpeed(evCapper(pokemon, stat, evValue)); 
        }
        recalcTotal(pokemon);
    }

    // Getters for EV Values, based on Stat enum
    public static int getEv(Pokemon pokemon, Stat stat) {
        return switch (stat) {
            case HP -> pokemon.getEvHp();
            case ATTACK -> pokemon.getEvAttack();
            case DEFENSE -> pokemon.getEvDefense();
            case SPECIAL_ATTACK -> pokemon.getEvSpecialAttack();
            case SPECIAL_DEFENSE -> pokemon.getEvSpecialDefense();
            case SPEED -> pokemon.getEvSpeed();
        };
    }


    public static int getTotalEv(Pokemon pokemon) {
        return pokemon.getEvTotal();
    }

    private void recalcTotal(Pokemon pokemon) {
        pokemon.setEvTotal(pokemon.getEvHp() + pokemon.getEvAttack() + pokemon.getEvDefense() +
                pokemon.getEvSpecialAttack() + pokemon.getEvSpecialDefense() + pokemon.getEvSpeed());
    }

    public static boolean checkEvTotals(Pokemon pokemon) {
        int total = getTotalEv(pokemon);
        if (total > 510) {
            LOGGER.warn("Total EVs exceed the maximum of 510. Current total: {}", total);
            return false;
        }
        if (pokemon.getEvHp() > 252 || pokemon.getEvAttack() > 252 || pokemon.getEvDefense() > 252 || pokemon.getEvSpecialAttack() > 252 || pokemon.getEvSpecialDefense() > 252 || pokemon.getEvSpeed() > 252) {
            LOGGER.warn("One or more EV stats exceed the maximum of 252. Current EVs - HP: {}, Attack: {}, Defense: {}, Special Attack: {}, Special Defense: {}, Speed: {}",
                    pokemon.getEvHp(), pokemon.getEvAttack(), pokemon.getEvDefense(), pokemon.getEvSpecialAttack(), pokemon.getEvSpecialDefense(), pokemon.getEvSpeed());
            return false;
        }
        return true;
    }

    // Adders
    public void addEv(Pokemon pokemon, Stat stat, int evToAdd) {
        int currentEv = getEv(pokemon, stat);
        int newEvValue = currentEv + evToAdd;
        LOGGER.info("Adding EV - Stat: {}, Current EV: {}, EV to Add: {}, New EV before capping: {}",
                stat, currentEv, evToAdd, newEvValue);
        
        switch (stat) {
            case HP -> pokemon.setEvHp(evAddable(pokemon, stat, evToAdd) + pokemon.getEvHp());
            case ATTACK -> pokemon.setEvAttack(evAddable(pokemon, stat, evToAdd) + pokemon.getEvAttack());
            case DEFENSE -> pokemon.setEvDefense(evAddable(pokemon, stat, evToAdd) + pokemon.getEvDefense());
            case SPECIAL_ATTACK -> pokemon.setEvSpecialAttack(evAddable(pokemon, stat, evToAdd) + pokemon.getEvSpecialAttack());
            case SPECIAL_DEFENSE -> pokemon.setEvSpecialDefense(evAddable(pokemon, stat, evToAdd) + pokemon.getEvSpecialDefense());
            case SPEED -> pokemon.setEvSpeed(evAddable(pokemon, stat, evToAdd) + pokemon.getEvSpeed());
        }
        recalcTotal(pokemon);
    }
    
}
