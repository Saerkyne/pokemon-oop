package pokemonGame;

/**
 * BattleAction — a sealed interface representing a player's chosen action for a battle turn.
 *
 * This file uses three Java 21 features that work together. They're explained below
 * in order from simplest to most advanced, building on each other.
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * FEATURE 1: INTERFACES (familiar ground)
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * An interface is a contract: "any class implementing me must provide these methods."
 * BattleAction says: every action has a trainer() and an activePokemon().
 *
 *   public interface BattleAction {
 *       Trainer trainer();
 *       Pokemon activePokemon();
 *   }
 *
 * Both MoveAction and SwitchAction implement it, so any code that receives a
 * BattleAction can safely call trainer() and activePokemon() without knowing
 * which specific type it is.
 *
 * But a regular interface has a problem: ANYONE can implement it. A third-party
 * class, a test stub, a mistake — the compiler can't help you know all the types.
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * FEATURE 2: SEALED (closing the set of allowed types)
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * Adding "sealed" + "permits" locks down exactly which classes can implement
 * the interface:
 *
 *   public sealed interface BattleAction permits MoveAction, SwitchAction { }
 *
 * Now the compiler KNOWS there are exactly two kinds of BattleAction. Period.
 * No one else can implement it. This has a powerful consequence: when you write
 * a switch statement over a BattleAction, the compiler can check that you've
 * handled every case.
 *
 * Think of it like an enum, but instead of each variant being just a name,
 * each variant is a full class with its own fields and methods.
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * FEATURE 3: RECORDS (concise immutable data classes)
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * A record is a class where you only declare the fields, and Java generates
 * the constructor, getters, equals(), hashCode(), and toString() for free.
 *
 * Compare:
 *
 *   // Without records — 30+ lines of boilerplate:
 *   public final class MoveAction implements BattleAction {
 *       private final Trainer trainer;
 *       private final Pokemon pokemon;
 *       private final int moveSlotIndex;
 *
 *       public MoveAction(Trainer trainer, Pokemon pokemon, int moveSlotIndex) {
 *           this.trainer = trainer;
 *           this.pokemon = pokemon;
 *           this.moveSlotIndex = moveSlotIndex;
 *       }
 *
 *       public Trainer trainer() { return trainer; }
 *       public Pokemon pokemon() { return pokemon; }
 *       public int moveSlotIndex() { return moveSlotIndex; }
 *       // plus equals(), hashCode(), toString()...
 *   }
 *
 *   // With records — one line:
 *   public record MoveAction(Trainer trainer, Pokemon pokemon, int moveSlotIndex)
 *       implements BattleAction { }
 *
 * Both produce the exact same class. The record just eliminates the repetition.
 *
 * Records are also immutable — once created, the fields can't change. This is
 * important for the battle loop: a BattleAction represents a choice that was
 * already made. It should never be modified after creation.
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * PUTTING IT TOGETHER: How TurnManager uses all three features
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * In TurnManager, you receive a BattleAction but need to do different things
 * depending on whether it's a MoveAction or SwitchAction.
 *
 * --- The old way (before Java 21): instanceof + manual cast ---
 *
 *   if (action instanceof MoveAction) {
 *       MoveAction ma = (MoveAction) action;       // cast
 *       MoveSlot slot = ma.getMoveSlot();           // now you can access fields
 *       resolveMove(ma, defender);
 *   } else if (action instanceof SwitchAction) {
 *       SwitchAction sa = (SwitchAction) action;   // cast
 *       resolveSwitch(sa);
 *   } else {
 *       // What goes here? With a regular interface, you can't be sure
 *       // there isn't some third implementation you missed.
 *   }
 *
 * --- The new way: pattern matching + sealed ---
 *
 *   switch (action) {
 *       case MoveAction ma   -> resolveMove(ma, defender);
 *       case SwitchAction sa -> resolveSwitch(sa);
 *   }
 *
 * What's happening:
 *   1. "case MoveAction ma" tests the type AND casts into variable 'ma' in one step.
 *   2. Because BattleAction is sealed, the compiler knows MoveAction and
 *      SwitchAction are the ONLY possibilities. No default case needed.
 *   3. If you later add a third action type (e.g., ItemAction) to the permits
 *      list, every switch statement that doesn't handle it becomes a compile
 *      error. The compiler forces you to handle the new type everywhere.
 *
 * --- Destructuring (one more level, optional but elegant) ---
 *
 * Records support "record patterns" — extracting fields inline:
 *
 *   switch (action) {
 *       case MoveAction(var trainer, var pokemon, var slotIndex) -> {
 *           // trainer, pokemon, and slotIndex are ready to use
 *           MoveSlot slot = pokemon.getMoveSet().get(slotIndex);
 *       }
 *       case SwitchAction(var trainer, var pokemon, var teamSlot) -> {
 *           Pokemon newPokemon = trainer.getTeam().get(teamSlot);
 *       }
 *   }
 *
 * You don't have to use destructuring — "case MoveAction ma ->" is perfectly
 * fine. Destructuring is just a convenience when you want direct access to the
 * record's fields without calling ma.moveSlotIndex().
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * WHY THIS MATTERS FOR THE BATTLE LOOP
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * The old approach (a single class with getActionType() returning "MOVE" or
 * "SWITCH" and sentinel values like -1 for inapplicable fields) works, but:
 *
 *   - Nothing stops you from calling getMoveSlotIndex() on a switch action
 *     and getting a meaningless -1. The compiler can't help.
 *   - String comparisons ("MOVE".equals(...)) can have typos that compile fine.
 *   - Adding a third action type means hunting through every if/else chain.
 *
 * The sealed interface + records approach makes illegal states unrepresentable:
 *   - MoveAction has moveSlotIndex. SwitchAction has teamSlotIndex. Neither
 *     has fields that don't belong to it.
 *   - The compiler enforces exhaustive handling of all action types.
 *   - Each type is immutable and self-documenting.
 */
public sealed interface BattleAction permits MoveAction, SwitchAction {

    Trainer trainer();
    Pokemon activePokemon();
    String getActionType();
}
