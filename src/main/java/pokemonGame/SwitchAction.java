package pokemonGame;

/**
 * A player's choice to switch their active Pokémon during a battle turn.
 * Immutable record — captures the trainer, their current active Pokémon,
 * and which team slot (0–5) to switch to.
 *
 * @see BattleAction
 * @see MoveAction
 */
public record SwitchAction(Trainer trainer, Pokemon pokemon, int teamSlotIndex) implements BattleAction {

    public Pokemon getSwitchPokemon() {
        return trainer.getTeam().getTeamSlot(teamSlotIndex);
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
}
