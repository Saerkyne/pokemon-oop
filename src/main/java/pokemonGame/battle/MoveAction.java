package pokemonGame.battle;

import java.util.Objects;

import pokemonGame.model.Move;
import pokemonGame.model.MoveSlot;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Trainer;
import pokemonGame.model.Team;

/**
 * A player's choice to use a move during a battle turn.
 * Immutable record — captures the trainer, their active Pokémon, and which
 * move slot (0–3) was selected.
 *
 * @see BattleAction
 * @see SwitchAction
 */
public record MoveAction(Trainer trainer, Pokemon pokemon, Team team, int moveSlotIndex) implements BattleAction {

    public MoveAction {
        if (moveSlotIndex < 0 || moveSlotIndex > 3) {
            throw new IllegalArgumentException("Move slot index must be between 0 and 3.");
        }
        Objects.requireNonNull(trainer, "Trainer cannot be null.");
        Objects.requireNonNull(pokemon, "Pokemon cannot be null.");
        Objects.requireNonNull(team, "Team cannot be null.");
    }
    
    public int getMoveSlotIndex() {
        return moveSlotIndex;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public Pokemon activePokemon() {
        return pokemon;
    }

    public MoveSlot getMoveSlot() {
        // This method can be implemented to return the MoveSlot being used for this MoveAction
        return pokemon.getMoveSet().get(moveSlotIndex);
    }

    public Move getMove() {
        // This method can be implemented to return the Move being used for this MoveAction
        return getMoveSlot().getMove();
    }

    public String getActionType() {
        return "MOVE";
    }

    public Team team() {
        return team;
    }

}
