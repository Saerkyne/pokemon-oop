package pokemonGame.bot.refactor.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import pokemonGame.model.Trainer;
import pokemonGame.model.Team;
import pokemonGame.bot.refactor.SlashCommandContext;
import pokemonGame.bot.refactor.SlashCommandHandler;
import pokemonGame.bot.refactor.SlashCommandSupport;

import net.dv8tion.jda.api.entities.User;

import pokemonGame.service.TrainerService;
import pokemonGame.service.TeamService;
import pokemonGame.service.BattleService;

public class StartBattleSlashCommand extends SlashCommandSupport implements SlashCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartBattleSlashCommand.class);
    private static final String MISSING_TEAM_MESSAGE = "Please specify the team you want to use.";
    private static final String MISSING_OPPONENT_MESSAGE = "Please specify the opponent you want to battle against.";

    private final TrainerService trainerService;
    private final TeamService teamService;
    private final BattleService battleService;

    public StartBattleSlashCommand(TrainerService trainerService, TeamService teamService, BattleService battleService) {
        this.trainerService = trainerService;
        this.teamService = teamService;
        this.battleService = battleService;
    }

    @Override
    public String commandName() {
        return "startbattle";
    }

    @Override
    public void handle(SlashCommandContext context) {
        LOGGER.info(
            "Recieved slash command; '{}' from user: {} (ID: {})",
            context.commandName(),
            context.user().getName(),
            context.userId()
        );

        Optional<Trainer> challenger = requireTrainer(
            context,
            trainerService,
            MISSING_OPPONENT_MESSAGE
        );
        if (challenger.isEmpty()) {
            return;
        }

        Optional<Team> team = requireTeam(
            context,
            teamService,
            challenger.get(),
            "teamname",
            MISSING_TEAM_MESSAGE,
            "No existing team found. Create one with /createteam!"
        );
        if (team.isEmpty()) {
            return;
        }

        Optional<User> opponent = requireUserOption(
            context,
            "opponent",
            MISSING_OPPONENT_MESSAGE
        );
        if (opponent.isEmpty()) {
            return;
        }

        Trainer opponentTrainer = trainerService.getTrainerByDiscordId(opponent.get().getIdLong());

        int createAttempt = battleService.createBattle(
            challenger.get().getTrainerDbId(),
            opponentTrainer.getTrainerDbId(),
            team.get().getTeamDbId()
        );
        if (createAttempt == -1) {
            replyEphemeral(context, "Failed to create battle. Please try again later.");
            return;
        }

        reply(context, "Battle created successfully! Waiting for an opponent to join...");
    }

}
