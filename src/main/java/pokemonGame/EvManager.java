package pokemonGame;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            case HP -> pokemon.evHp;
            case ATTACK -> pokemon.evAttack;
            case DEFENSE -> pokemon.evDefense;
            case SPECIAL_ATTACK -> pokemon.evSpecialAttack;
            case SPECIAL_DEFENSE -> pokemon.evSpecialDefense;
            case SPEED -> pokemon.evSpeed;
        };
    }


    public static int getTotalEv(Pokemon pokemon) {
        return pokemon.evTotal;
    }

    private void recalcTotal(Pokemon pokemon) {
        pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
    }

    public static boolean checkEvTotals(Pokemon pokemon) {
        int total = getTotalEv(pokemon);
        if (total > 510) {
            LOGGER.warn("Total EVs exceed the maximum of 510. Current total: {}", total);
            return false;
        }
        if (pokemon.evHp > 252 || pokemon.evAttack > 252 || pokemon.evDefense > 252 || pokemon.evSpecialAttack > 252 || pokemon.evSpecialDefense > 252 || pokemon.evSpeed > 252) {
            LOGGER.warn("One or more EV stats exceed the maximum of 252. Current EVs - HP: {}, Attack: {}, Defense: {}, Special Attack: {}, Special Defense: {}, Speed: {}",
                    pokemon.evHp, pokemon.evAttack, pokemon.evDefense, pokemon.evSpecialAttack, pokemon.evSpecialDefense, pokemon.evSpeed);
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
            case HP -> pokemon.setEvHp(evAddable(pokemon, stat, evToAdd) + pokemon.evHp);
            case ATTACK -> pokemon.setEvAttack(evAddable(pokemon, stat, evToAdd) + pokemon.evAttack);
            case DEFENSE -> pokemon.setEvDefense(evAddable(pokemon, stat, evToAdd) + pokemon.evDefense);
            case SPECIAL_ATTACK -> pokemon.setEvSpecialAttack(evAddable(pokemon, stat, evToAdd) + pokemon.evSpecialAttack);
            case SPECIAL_DEFENSE -> pokemon.setEvSpecialDefense(evAddable(pokemon, stat, evToAdd) + pokemon.evSpecialDefense);
            case SPEED -> pokemon.setEvSpeed(evAddable(pokemon, stat, evToAdd) + pokemon.evSpeed);
        }
        recalcTotal(pokemon);
    }
    
}
