package pokemonGame;

import java.util.Random;

/**
 * Represents the 25 possible Pokémon natures.  Each nature boosts one stat by
 * 10% and lowers another by 10%.  Neutral natures have neither boost nor
 * penalty.  Storing the parameters directly in the enum makes lookups trivial
 * (no separate list or search needed) and eliminates redundant fields.
 */
public enum Natures {
    ADAMANT("Adamant", Stat.ATTACK, Stat.SPECIAL_ATTACK),
    BASHFUL("Bashful", null, null),            // neutral
    BOLD("Bold", Stat.DEFENSE, Stat.ATTACK),
    BRAVE("Brave", Stat.ATTACK, Stat.SPEED),
    CALM("Calm", Stat.SPECIAL_DEFENSE, Stat.ATTACK),
    CAREFUL("Careful", Stat.SPECIAL_DEFENSE, Stat.SPECIAL_ATTACK),
    DOCILE("Docile", null, null),
    GENTLE("Gentle", Stat.SPECIAL_DEFENSE, Stat.DEFENSE),
    HARDY("Hardy", null, null),
    HASTY("Hasty", Stat.SPEED, Stat.DEFENSE),
    IMPISH("Impish", Stat.DEFENSE, Stat.SPECIAL_ATTACK),
    JOLLY("Jolly", Stat.SPEED, Stat.SPECIAL_ATTACK),
    LAX("Lax", Stat.DEFENSE, Stat.SPECIAL_DEFENSE),
    LONELY("Lonely", Stat.ATTACK, Stat.DEFENSE),
    MILD("Mild", Stat.SPECIAL_ATTACK, Stat.DEFENSE),
    MODEST("Modest", Stat.SPECIAL_ATTACK, Stat.ATTACK),
    NAIVE("Naive", Stat.SPEED, Stat.SPECIAL_DEFENSE),             
    QUIET("Quiet", Stat.SPECIAL_ATTACK, Stat.SPEED),
    QUIRKY("Quirky", null, null),
    RASH("Rash", Stat.SPECIAL_ATTACK, Stat.SPECIAL_DEFENSE),
    RELAXED("Relaxed", Stat.DEFENSE, Stat.SPEED),
    SASSY("Sassy", Stat.SPECIAL_DEFENSE, Stat.SPEED),
    SERIOUS("Serious", null, null),
    TIMID("Timid", Stat.SPEED, Stat.ATTACK);

    private final String displayName;
    private final Stat increased;
    private final Stat decreased;

    Natures(String displayName, Stat increased, Stat decreased) {
        this.displayName = displayName;
        this.increased = increased;
        this.decreased = decreased;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the multiplier applied to the given stat by this nature.
     * 1.1 for the boosted stat, 0.9 for the dropped stat, otherwise 1.0.
     */
    public double modifierFor(Stat stat) {
        if (stat == increased) {
            return 1.1;
        }
        if (stat == decreased) {
            return 0.9;
        }
        return 1.0;
    }

    /**
     * Lookup by name (case‑insensitive).  Useful for reading a Pokémon’s nature
     * string and converting it back to an enum value.
     */
    public static Natures fromString(String name) {
        for (Natures n : values()) {
            if (n.displayName.equalsIgnoreCase(name) || n.name().equalsIgnoreCase(name)) {
                return n;
            }
        }
        throw new IllegalArgumentException("Unknown nature: " + name);
    }

    /**
     * Convenience helper to apply this nature to a Pokémon instance.  It sets
     * the nature field. 
     */
    public void assignTo(Pokemon p) {
        p.setNature(this);
    }

    // --- randomization helpers -------------------------------------------

    private static final Random RNG = new Random();

    /**
     * Return a random nature.  Useful when creating a wild or newly hatched
     * Pokémon.
     */
    public static Natures random() {
        Natures[] all = values();
        return all[RNG.nextInt(all.length)];
    }

    /**
     * Assign a random nature to the given Pokémon (sets its field, caller can
     * then recalc stats if desired).
     */
    public static void assignRandom(Pokemon p) {
        random().assignTo(p);
    }
}
