package pokemonGame.battle;

import pokemonGame.model.Trainer;

/**
 * Immutable snapshot of everything that happened during one battle turn.
 * Returned by {@link TurnManager} and used by the bot layer to format
 * the turn summary for both players.
 *
 * @param action1Result  result of the first (faster) action, or {@code null} if none
 * @param action2Result  result of the second action, or {@code null} if skipped (e.g. faint)
 * @param battleOver     whether the battle ended this turn
 * @param winner         the winning {@link Trainer}, or {@code null} if the battle continues
 */
public record TurnResult(DamageResult action1Result, DamageResult action2Result, boolean battleOver, Trainer winner) {
    public TurnResult {
        if (action1Result == null && action2Result != null) {
            throw new IllegalArgumentException("Action 2 cannot have a result if Action 1 has no result");
        }
        if (battleOver && winner == null) {
            throw new IllegalArgumentException("Winner must be specified if the battle is over");
        }
        if (!battleOver && winner != null) {
            throw new IllegalArgumentException("Winner must be null if the battle is not over");
        }
    }
}
