package pokemonGame.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.model.Pokemon;

/**
 * Calculates derived stats (HP, Attack, Defense, Sp.Atk, Sp.Def, Speed) from
 * base stats, IVs, EVs, level, and nature using the Gen III+ formula.
 * All methods are static — no instance state.
 *
 * <p>HP formula: {@code ((2*base + IV + EV/4) * level / 100) + level + 10}<br>
 * Other stats: {@code (((2*base + IV + EV/4) * level / 100) + 5) * natureModifier}</p>
 *
 * @see EvManager
 * @see Natures
 */
public class StatCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatCalculator.class);

    // Method for calculating Max HP
    public static int calcMaxHP(int hpBase, int level, int ivHp, int ev) {
        LOGGER.info("Calculating max HP with base HP: {}, level: {}, IV HP: {}, EV HP: {}", hpBase, level, ivHp, ev);

        // Calculating the actual HP of a Pokemon requires the base HP stat, the Pokemon's level, 
        // and its individual values (IVs) and effort values (EVs).
        // The formula is: HP = ((((2 * baseHP) + IV + (EV / 4)) * Level) / 100) + Level + 10
        // We want an integer, so we are going to do the calculations in steps to ensure we don't lose 
        // precision until the end when we can round down to an integer.
        int evContrib = ev / 4;
        int baseIVContrib =(2 * hpBase) + ivHp + evContrib;
        int levelContrib = baseIVContrib * level;
        int division = levelContrib / 100;
        int calcHP = division + level + 10;
        return calcHP;
    }

    // Method for calculating other stats (Attack, Defense, etc.)
    public static int calcCurrentStat(int baseStat, int level, int iv, int ev, double natureModifier) {
        // The formula for calculating the current value of a non-HP stat is: 
        // Stat = (((((2 * baseStat) + IV + (EV/4)) * Level) / 100) + 5) * Nature
        // For now, we will ignore the Nature modifier and just calculate the stat without it.
        // A 1 will be used as a placeholder for the Nature modifier in the future when that is implemented.
        int evContrib = ev / 4;
        int baseIVContrib = (2 * baseStat) + iv + evContrib;
        int levelContrib = baseIVContrib * level;
        int division = levelContrib / 100;
        int calcStat = (int) Math.floor((division + 5) * natureModifier);
        return calcStat;
    }

    // Wrapper method for calculating all stats at once
    public static void calculateAllStats(Pokemon pokemon) {
        pokemon.setCurrentAttack(calcCurrentStat(pokemon.getAttackBaseStat(), pokemon.getLevel(), pokemon.getIvAttack(), EvManager.getEv(pokemon, Stat.ATTACK), pokemon.getNature().modifierFor(Stat.ATTACK)));
        pokemon.setCurrentDefense(calcCurrentStat(pokemon.getDefenseBaseStat(), pokemon.getLevel(), pokemon.getIvDefense(), EvManager.getEv(pokemon, Stat.DEFENSE), pokemon.getNature().modifierFor(Stat.DEFENSE)));
        pokemon.setCurrentSpecialAttack(calcCurrentStat(pokemon.getSpecialAttackBaseStat(), pokemon.getLevel(), pokemon.getIvSpecialAttack(), EvManager.getEv(pokemon, Stat.SPECIAL_ATTACK), pokemon.getNature().modifierFor(Stat.SPECIAL_ATTACK)));
        pokemon.setCurrentSpecialDefense(calcCurrentStat(pokemon.getSpecialDefenseBaseStat(), pokemon.getLevel(), pokemon.getIvSpecialDefense(), EvManager.getEv(pokemon, Stat.SPECIAL_DEFENSE), pokemon.getNature().modifierFor(Stat.SPECIAL_DEFENSE)));
        pokemon.setCurrentSpeed(calcCurrentStat(pokemon.getSpeedBaseStat(), pokemon.getLevel(), pokemon.getIvSpeed(), EvManager.getEv(pokemon, Stat.SPEED), pokemon.getNature().modifierFor(Stat.SPEED)));
        
        int oldMaxHP = pokemon.getMaxHP();
        pokemon.setMaxHP(calcMaxHP(pokemon.getHpBaseStat(), pokemon.getLevel(), pokemon.getIvHp(), EvManager.getEv(pokemon, Stat.HP)));

        if (oldMaxHP == 0) {
            // If this is the first time calculating stats, set current HP to max HP
            pokemon.setCurrentHP(pokemon.getMaxHP());
        } else {
            // Otherwise, we might add logic here to adjust current HP proportionally, but for now we will just leave current HP as is when max HP changes.
            // This means that if you change EVs or IVs in a way that changes max HP, your current HP will not automatically adjust to the new max HP. This is something that could
            // be changed in the future if desired, but for now we will just keep it simple and not implement that logic.

            LOGGER.info("Max HP changed from {} to {}. Current HP remains at {}.", oldMaxHP, pokemon.getMaxHP(), pokemon.getCurrentHP());
        }


    }
}
