package pokemonGame;

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



}
