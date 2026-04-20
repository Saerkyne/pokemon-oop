package pokemonGame.battle;

import java.util.Objects;

import pokemonGame.model.Pokemon;
import pokemonGame.model.Trainer;
import pokemonGame.model.Team;

/**
 * A player's choice to switch their active Pokémon during a battle turn.
 * Immutable record — captures the trainer, their current active Pokémon,
 * and which team slot (0–5) to switch to.
 *
 * @see BattleAction
 * @see MoveAction
 */
public record SwitchAction(Trainer trainer, Pokemon pokemon, Team team, int teamSlotIndex) implements BattleAction {

    public SwitchAction {
        if (teamSlotIndex < 0 || teamSlotIndex > 5) {
            throw new IllegalArgumentException("Team slot index must be between 0 and 5.");
        }
        Objects.requireNonNull(trainer, "Trainer cannot be null.");
        Objects.requireNonNull(pokemon, "Pokemon cannot be null.");
        Objects.requireNonNull(team, "Team cannot be null.");
        // TODO(review 2026-04-20): Enforce battle-rule validation here, not only index bounds.
        // Same-slot, empty-slot, and fainted-target switches currently pass record construction and fail late or behave oddly during turn resolution.

    }

    public Pokemon getSwitchPokemon() {
        return team.getTeamSlot(teamSlotIndex);
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public Pokemon activePokemon() {
        return pokemon;
    }

    public String getActionType() {
        return "SWITCH";
    }

    public Team getTeam() {
        return team;
    }
}
