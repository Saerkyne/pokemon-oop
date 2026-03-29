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
            case HP:
                pokemon.evHp = evCapper(pokemon, stat, evValue);
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case ATTACK:
                pokemon.evAttack = evCapper(pokemon, stat, evValue);
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case DEFENSE:
                pokemon.evDefense = evCapper(pokemon, stat, evValue);
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case SPECIAL_ATTACK:
                pokemon.evSpecialAttack = evCapper(pokemon, stat, evValue);
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case SPECIAL_DEFENSE:
                pokemon.evSpecialDefense = evCapper(pokemon, stat, evValue);
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case SPEED:
                pokemon.evSpeed = evCapper(pokemon, stat, evValue);
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
        }
    }

    // Getters for EV Values, based on Stat enum
    public static int getEv(Pokemon pokemon, Stat stat) {
        switch (stat) {
            case HP:
                return pokemon.evHp;
            case ATTACK:
                return pokemon.evAttack;
            case DEFENSE:
                return pokemon.evDefense;
            case SPECIAL_ATTACK:
                return pokemon.evSpecialAttack;
            case SPECIAL_DEFENSE:
                return pokemon.evSpecialDefense;
            case SPEED:
                return pokemon.evSpeed;
            default:
                LOGGER.warn("Attempted to get invalid Stat value");
                throw new IllegalArgumentException("Invalid stat: " + stat);
        }
    }

    public static int getTotalEv(Pokemon pokemon) {
        return pokemon.evTotal;
    }

    public static boolean checkEvTotals(Pokemon pokemon) {
        int total = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
        if (total > 510) {
            LOGGER.warn("Total EVs exceed the maximum of 510. Current total: " + total);
            return false;
        }
        if (pokemon.evHp > 252 || pokemon.evAttack > 252 || pokemon.evDefense > 252 || pokemon.evSpecialAttack > 252 || pokemon.evSpecialDefense > 252 || pokemon.evSpeed > 252) {
            LOGGER.warn("One or more EV stats exceed the maximum of 252. Current EVs - HP: " + pokemon.evHp + ", Attack: " + pokemon.evAttack + ", Defense: " + pokemon.evDefense + ", Special Attack: " + pokemon.evSpecialAttack + ", Special Defense: " + pokemon.evSpecialDefense + ", Speed: " + pokemon.evSpeed);
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
                case HP:
                pokemon.evHp = evAddable(pokemon, stat, evToAdd) + pokemon.evHp;
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case ATTACK:
                pokemon.evAttack = evAddable(pokemon, stat, evToAdd) + pokemon.evAttack;
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case DEFENSE:
                pokemon.evDefense = evAddable(pokemon, stat, evToAdd) + pokemon.evDefense;
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case SPECIAL_ATTACK:
                pokemon.evSpecialAttack = evAddable(pokemon, stat, evToAdd) + pokemon.evSpecialAttack;
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case SPECIAL_DEFENSE:
                pokemon.evSpecialDefense = evAddable(pokemon, stat, evToAdd) + pokemon.evSpecialDefense;
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
            case SPEED:
                pokemon.evSpeed = evAddable(pokemon, stat, evToAdd) + pokemon.evSpeed;
                pokemon.evTotal = pokemon.evHp + pokemon.evAttack + pokemon.evDefense +
                        pokemon.evSpecialAttack + pokemon.evSpecialDefense + pokemon.evSpeed;
                break;
        }
    }
    

    
}
