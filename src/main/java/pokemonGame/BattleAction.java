package pokemonGame;

import java.sql.Timestamp;

/**
 * BattleAction — a sealed interface representing a player's chosen action for a battle turn.
 *
 * "sealed" means only the classes listed after "permits" can implement this interface.
 * The compiler enforces this — no other class can implement BattleAction.
 *
 * Why sealed? When you switch over a BattleAction, the compiler knows every possible type,
 * so it can verify you've handled them all. If you later add a new action type to the
 * permits list, every switch that doesn't handle it becomes a compile error — bugs caught
 * at compile time, not runtime.
 *
 * The permitted types (MoveAction, SwitchAction) are records — immutable data carriers.
 * Records auto-generate the constructor, getters, equals(), hashCode(), and toString().
 * Immutability means once created, they can't be changed — safe to pass between layers.
 *
 * --- Example: Record declarations implementing this sealed interface ---
 *
 *   public record MoveAction(int moveSlotIndex) implements BattleAction { }
 *   public record SwitchAction(int switchPokemonId) implements BattleAction { }
 *
 * --- Example: Without sealed, you need a dead else branch ---
 *
 *   if (action instanceof MoveAction) {
 *       // handle move
 *   } else if (action instanceof SwitchAction) {
 *       // handle switch
 *   } else {
 *       // ??? what goes here? You'd never expect this case,
 *       // but the compiler doesn't know that.
 *   }
 *
 * --- Example: With sealed + pattern matching switch (exhaustive, no default needed) ---
 *
 *   switch (action) {
 *       case MoveAction ma  -> executeMoveAction(ma);
 *       case SwitchAction sa -> executeSwitchAction(sa);
 *       // No default needed — the compiler knows this is exhaustive.
 *   }
 *
 * --- Example: Record pattern matching (destructuring) in the battle loop ---
 *
 *   switch (action) {
 *       case MoveAction(var slotIndex) -> {
 *           // slotIndex is extracted directly — no casting needed
 *           MoveSlot slot = attacker.getMoveSet().get(slotIndex);
 *           // calculate and apply damage...
 *       }
 *       case SwitchAction(var pokemonId) -> {
 *           // pokemonId is extracted directly
 *           // swap the active Pokémon...
 *       }
 *   }
 *
 * --- Example: The old way without records or pattern matching ---
 *
 *   if (action instanceof MoveAction) {
 *       MoveAction ma = (MoveAction) action;  // manual cast
 *       int slotIndex = ma.moveSlotIndex();   // manual getter call
 *   }
 *
 * Progression: sealed interface locks down the type set → records make each variant
 * concise and immutable → pattern matching switch gives compiler-checked branching.
 */
public sealed interface BattleAction permits MoveAction, SwitchAction {

    /*
     * SUGGESTION: Strip this interface down to only what's truly shared between
     * MoveAction and SwitchAction. Right now it has 8 methods, but most are
     * type-specific — getMoveSlotIndex() returns -1 for SwitchAction, and
     * getSwitchPokemonId() returns -1 for MoveAction. That pattern (sentinel
     * values for "not applicable") is exactly what sealed + pattern matching
     * is designed to eliminate.
     *
     * The whole point of having separate record types is that each type carries
     * only its own data. The caller uses pattern matching to get at it:
     *
     *   switch (action) {
     *       case MoveAction ma   -> resolveMove(ma, defender);
     *       case SwitchAction sa -> resolveSwitch(sa);
     *   }
     *
     * Inside resolveMove(), you call ma.moveSlotIndex() directly — no interface
     * method needed. Inside resolveSwitch(), you call sa.teamSlotIndex() directly.
     * No -1 sentinels, no null returns, no getActionType() string comparison.
     *
     * REMOVE these methods:
     *   - getActionType()      → the type IS the action type (instanceof / pattern match)
     *   - getSubmittedAt()     → persistence concern, belongs in BattleTurnCRUD
     *   - getMoveSlotIndex()   → only meaningful on MoveAction, access via pattern match
     *   - getSwitchPokemonId() → only meaningful on SwitchAction, access via pattern match
     *   - getMoveSlot()        → only meaningful on MoveAction, access via pattern match
     *   - getMove()            → only meaningful on MoveAction, access via pattern match
     *
     * KEEP only what both action types genuinely share:
     *   - trainer()           → who performed the action
     *   - activePokemon()     → the Pokémon that was on the field when the action was chosen
     *
     * Simplified interface:
     *
     *   public sealed interface BattleAction permits MoveAction, SwitchAction {
     *       Trainer trainer();
     *       Pokemon activePokemon();
     *   }
     */

    String getActionType();
    Timestamp getSubmittedAt();
    int getMoveSlotIndex(); // Only valid for MoveAction, can return -1 for SwitchAction
    int getSwitchPokemonId(); // Only valid for SwitchAction, can return -1 for MoveAction
    Trainer getTrainer();
    Pokemon getCurrentPokemon();
    MoveSlot getMoveSlot(); // This can be implemented to return the MoveSlot being used for a MoveAction, or null for SwitchAction
    Move getMove();
}
