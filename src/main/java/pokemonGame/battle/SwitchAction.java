package pokemonGame.battle;

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
