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

    private EvManager() {
        // Private constructor to prevent instantiation
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EvManager.class);
    private static final int MAX_EV_PER_STAT = 252;
    private static final int MAX_TOTAL_EV = 510;


    // Method for calculating how much EV can be added without exceeding caps for any EV
    private static int evAddable(Pokemon pokemon, Stat stat, int newEvValue) {
        int currentEvValue = getEv(pokemon, stat);
        int currentEvTotal = getTotalEv(pokemon);
        int roomInStat = MAX_EV_PER_STAT - currentEvValue;
        int roomInTotal = MAX_TOTAL_EV - currentEvTotal;
        int maxRoom = Math.min(roomInStat, roomInTotal);
        int cappedAdd = Math.min(newEvValue, maxRoom);
        int maximumAddableValue = Math.max(0, cappedAdd);
        return maximumAddableValue;
    }

    // Method for overwriting EV values, with capping logic to ensure no caps are exceeded
    private static int evCapper(Pokemon pokemon, Stat stat, int newEvValue) {
        int currentEvValue = getEv(pokemon, stat);
        int currentEvTotal = getTotalEv(pokemon);
        int totalWithoutThisStat = currentEvTotal - currentEvValue;
        int roomInTotal = MAX_TOTAL_EV - totalWithoutThisStat;
        int cappedValue = Math.min(newEvValue, Math.min(MAX_EV_PER_STAT, roomInTotal));
        int finalCappedValue = Math.max(0, cappedValue);
        return finalCappedValue;
    }


    public static void setEv(Pokemon pokemon, Stat stat, int evValue) {
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

    private static void recalcTotal(Pokemon pokemon) {
        pokemon.setEvTotal(pokemon.getEvHp() + pokemon.getEvAttack() + pokemon.getEvDefense() +
                pokemon.getEvSpecialAttack() + pokemon.getEvSpecialDefense() + pokemon.getEvSpeed());
    }

    public static boolean checkEvTotals(Pokemon pokemon) {
        int total = getTotalEv(pokemon);
        if (total > MAX_TOTAL_EV) {
            LOGGER.warn("Total EVs exceed the maximum of {}. Current total: {}", MAX_TOTAL_EV, total);
            return false;
        }
        if (pokemon.getEvHp() > MAX_EV_PER_STAT || pokemon.getEvAttack() > MAX_EV_PER_STAT || pokemon.getEvDefense() > MAX_EV_PER_STAT || pokemon.getEvSpecialAttack() > MAX_EV_PER_STAT || pokemon.getEvSpecialDefense() > MAX_EV_PER_STAT || pokemon.getEvSpeed() > MAX_EV_PER_STAT) {
            LOGGER.warn("One or more EV stats exceed the maximum of {}. Current EVs - HP: {}, Attack: {}, Defense: {}, Special Attack: {}, Special Defense: {}, Speed: {}",
                    MAX_EV_PER_STAT, pokemon.getEvHp(), pokemon.getEvAttack(), pokemon.getEvDefense(), pokemon.getEvSpecialAttack(), pokemon.getEvSpecialDefense(), pokemon.getEvSpeed());
            return false;
        }
        return true;
    }

    // Adders
    public static void addEv(Pokemon pokemon, Stat stat, int evToAdd) {
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
