package pokemonGame;

/**
 * Simple enum for the six stats that can be modified by natures.  Having
 * a dedicated type makes the {@link Natures#modifierFor} method clean and
 * prevents typos when calculating multipliers.
 */
public enum Stat {
    HP,
    ATTACK,
    DEFENSE,
    SPECIAL_ATTACK,
    SPECIAL_DEFENSE,
    SPEED
}
