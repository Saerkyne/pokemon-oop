package pokemonGame.battle;

/**
 * Immutable snapshot of the outcome of a single damage calculation.
 * Produced by {@link Attack} and consumed by {@link TurnManager} to
 * build a {@link TurnResult}.
 *
 * @param damage           the amount of HP subtracted from the defender
 * @param effectiveness    the type effectiveness multiplier (0, 0.25, 0.5, 1, 2, or 4)
 * @param isCritical       whether the hit was a critical hit
 * @param isHit            whether the move connected (false = missed)
 * @param defenderFainted  whether the defender's HP reached 0
 */
public record DamageResult(int damage, float effectiveness, boolean isCritical, boolean isHit, boolean defenderFainted) {
    public DamageResult {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        if (effectiveness != 0 && effectiveness != 0.25f && effectiveness != 0.5f && effectiveness != 1f && effectiveness != 2f && effectiveness != 4f) {
            throw new IllegalArgumentException("Effectiveness must be one of: 0, 0.25, 0.5, 1, 2, or 4");
        }
        if (!isHit && damage != 0) {
            throw new IllegalArgumentException("Damage must be 0 if the move missed");
        }
        if (!isHit && isCritical) {
            throw new IllegalArgumentException("A move cannot be a critical hit if it missed");
        }
    }
}
