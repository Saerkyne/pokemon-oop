package pokemonGame.bot.refactor.commands;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.bot.refactor.SlashCommandContext;
import pokemonGame.bot.refactor.SlashCommandHandler;
import pokemonGame.bot.refactor.SlashCommandSupport;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.model.Trainer;
import pokemonGame.service.TeamService;
import pokemonGame.service.TrainerService;

public final class ReleasePokemonSlashCommand extends SlashCommandSupport implements SlashCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReleasePokemonSlashCommand.class);

    private final TeamService teamService;
    private final TrainerService trainerService;

    public ReleasePokemonSlashCommand(TeamService teamService, TrainerService trainerService) {
        this.teamService = Objects.requireNonNull(teamService);
        this.trainerService = Objects.requireNonNull(trainerService);
    }

    @Override
    public String commandName() {
        return "releasepokemon";
    }

    @Override
    public void handle(SlashCommandContext context) {
        Optional<String> pokemonName = requireStringOption(
            context,
            "pokemon",
            "Please specify the Pokémon you want to release.");
        if (pokemonName.isEmpty()) {
            return;
        }

        LOGGER.info(
            "Received slash command; '{}' with (nick)name: '{}' from user: {} (ID: {})",
            context.commandName(),
            pokemonName.get(),
            context.user(),
            context.userId());

        Optional<Trainer> trainer = requireTrainer(
            context,
            trainerService,
            "You need to create a trainer first using /createtrainer!");
        if (trainer.isEmpty()) {
            return;
        }

        Optional<Team> team = requireTeam(
            context,
            teamService,
            trainer.get(),
            "teamname",
            "Please specify the team the Pokémon belongs to.",
            "Team not found! Please specify a valid team name.");
        if (team.isEmpty()) {
            return;
        }

        Optional<Pokemon> pokemonToRelease = requirePokemon(
            context,
            teamService,
            trainer.get(),
            team.get(),
            "pokemon",
            "Please specify the Pokémon you want to release.",
            "No Pokémon with that nickname found on your team! Make sure you entered the correct nickname.");
        if (pokemonToRelease.isEmpty()) {
            return;
        }

        int releasedSlotIndex = pokemonToRelease.get().getCurrentTeamSlotIndex();
        boolean releaseSuccess = teamService.releasePokemon(team.get(), pokemonToRelease.get());
        if (!releaseSuccess) {
            reply(context, "Sorry, there was an error releasing that Pokémon from your team! Make sure you entered a valid slot index.");
            return;
        }

        reply(
            context,
            "Successfully released " + pokemonToRelease.get().getNickname()
                + " from slot " + (releasedSlotIndex + 1) + " of your team!");
    }
}