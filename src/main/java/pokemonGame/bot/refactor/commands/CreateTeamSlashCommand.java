package pokemonGame.bot.refactor.commands;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.bot.refactor.SlashCommandContext;
import pokemonGame.bot.refactor.SlashCommandHandler;
import pokemonGame.bot.refactor.SlashCommandSupport;
import pokemonGame.service.TeamService;
import pokemonGame.service.TrainerService;
import pokemonGame.model.Trainer;
import pokemonGame.model.Team;

public final class CreateTeamSlashCommand extends SlashCommandSupport implements SlashCommandHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTeamSlashCommand.class);

    private final TeamService teamService;
    private final TrainerService trainerService;

    public CreateTeamSlashCommand(TeamService teamService, TrainerService trainerService) {
        this.teamService = Objects.requireNonNull(teamService);
        this.trainerService = Objects.requireNonNull(trainerService);
    }

    @Override
    public String commandName() {
        return "createteam";
    }

    @Override
    public void handle(SlashCommandContext context) {
        
        Optional<Trainer> trainer = requireTrainer(context, trainerService, "No existing trainer found. Create one with /createtrainer!");
        if (trainer.isEmpty()) {
            return;
        }
        
        Optional<String> teamName = requireStringOption(
            context,
            "teamname",
            "You must provide a team name");
        if (teamName.isEmpty()) {
            return;
        }

        LOGGER.info(
            "Recieved slash command; '{}' with content: '{}' from user: {} (ID: {})",
            context.commandName(),
            teamName.get(),
            context.user(),
            context.userId());
        
        Team createAttempt = teamService.createTeam(
            trainer.get().getTrainerDbId(),
            teamName.get());

        if (createAttempt == null) {
            replyEphemeral(context, "Failed to create team.");
            return;
        }

        context.event().reply(
            "Team created successfully! Use /checkteam to see your current teams and /addpokemon to add Pokemon to them!")
            .queue();
    }
}
