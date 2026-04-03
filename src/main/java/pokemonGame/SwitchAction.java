package pokemonGame;

public record SwitchAction(Trainer trainer, Pokemon pokemon, int teamSlotIndex) implements BattleAction {

    public Pokemon getSwitchPokemon() {
        return trainer.getTeam().get(teamSlotIndex);
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
