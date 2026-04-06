package pokemonGame;

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
}
