package pokemonGame.bot.refactor;

import java.util.Optional;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.entities.User;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.model.Trainer;
import pokemonGame.service.TeamService;
import pokemonGame.service.TrainerService;

/**
 * Shared bot-layer helpers for routed slash command handlers.
 */
public abstract class SlashCommandSupport {

    protected void reply(SlashCommandContext context, String message) {
        if (context.event().isAcknowledged()) {
            context.event().getHook().sendMessage(message).queue();
            return;
        }

        context.event().reply(message).queue();
    }

    protected Optional<String> requireStringOption(SlashCommandContext context, String optionName, String missingMessage) {
        OptionMapping option = context.event().getOption(optionName);
        if (option == null) {
            replyEphemeral(context, missingMessage);
            return Optional.empty();
        }

        String value = option.getAsString();
        if (value == null || value.isBlank()) {
            replyEphemeral(context, missingMessage);
            return Optional.empty();
        }

        return Optional.of(value);
    }

    /*
     * Migration scaffolding for more complex commands.
     * Commands like addpokemon and teachmoveset repeat trainer/team/Pokemon lookup.
     * These helpers centralize that glue so future handlers can spend more code on
     * command-specific behavior and less code on defensive checks.
     */

    /**
     * Shared trainer lookup for commands that require an existing trainer record.
     */
    protected Optional<Trainer> requireTrainer(
        SlashCommandContext context,
        TrainerService trainerService,
        String missingTrainerMessage) {
        Trainer trainer = trainerService.getTrainerByDiscordId(context.userId());
        if (trainer == null) {
            replyEphemeral(context, missingTrainerMessage);
            return Optional.empty();
        }

        return Optional.of(trainer);
    }

    /**
     * Shared team lookup that first validates the team option, then loads the team.
     */
    protected Optional<Team> requireTeam(
        SlashCommandContext context,
        TeamService teamService,
        Trainer trainer,
        String teamOptionName,
        String missingTeamOptionMessage,
        String missingTeamMessage) {
        Optional<String> teamName = requireStringOption(context, teamOptionName, missingTeamOptionMessage);
        if (teamName.isEmpty()) {
            return Optional.empty();
        }

        Team team = teamService.getTeamFromName(trainer.getTrainerDbId(), teamName.get());
        if (team == null) {
            replyEphemeral(context, missingTeamMessage);
            return Optional.empty();
        }

        return Optional.of(team);
    }

    /**
     * Shared Pokemon lookup that validates nickname input against a selected team.
     */
    protected Optional<Pokemon> requirePokemon(
        SlashCommandContext context,
        TeamService teamService,
        Trainer trainer,
        Team team,
        String pokemonOptionName,
        String missingPokemonOptionMessage,
        String missingPokemonMessage) {
        Optional<String> pokemonName = requireStringOption(context, pokemonOptionName, missingPokemonOptionMessage);
        if (pokemonName.isEmpty()) {
            return Optional.empty();
        }

        Pokemon pokemon = teamService.getPokemonByNickname(
            trainer.getTrainerDbId(),
            team.getTeamDbId(),
            pokemonName.get());
        if (pokemon == null) {
            replyEphemeral(context, missingPokemonMessage);
            return Optional.empty();
        }

        return Optional.of(pokemon);
    }

    protected Optional<User> requireUserOption(SlashCommandContext context, String optionName, String missingMessage) {
        OptionMapping option = context.event().getOption(optionName);
        if (option == null) {
            replyEphemeral(context, missingMessage);
            return Optional.empty();
        }

        User user = option.getAsUser();
        if (user == null) {
            replyEphemeral(context, missingMessage);
            return Optional.empty();
        }

        return Optional.of(user);
    }

    protected void replyEphemeral(SlashCommandContext context, String message) {
        if (context.event().isAcknowledged()) {
            context.event().getHook().sendMessage(message).setEphemeral(true).queue();
            return;
        }

        context.event().reply(message).setEphemeral(true).queue();
    }
}