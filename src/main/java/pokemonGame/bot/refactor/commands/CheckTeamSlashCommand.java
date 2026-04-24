package pokemonGame.bot.refactor.commands;

import java.util.List;
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

public final class CheckTeamSlashCommand extends SlashCommandSupport implements SlashCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckTeamSlashCommand.class);

    private final TeamService teamService;
    private final TrainerService trainerService;

    public CheckTeamSlashCommand(TeamService teamService, TrainerService trainerService) {
        this.teamService = Objects.requireNonNull(teamService);
        this.trainerService = Objects.requireNonNull(trainerService);
    }

    @Override
    public String commandName() {
        return "checkteam";
    }

    @Override
    public void handle(SlashCommandContext context) {
        LOGGER.info(
            "Received slash command; '{}' from user: {} (ID: {})",
            context.commandName(),
            context.user().getName(),
            context.userId());

        Optional<Trainer> trainer = requireTrainer(
            context,
            trainerService,
            "You need to create a trainer first using /createtrainer!");
        if (trainer.isEmpty()) {
            return;
        }

        Optional<Team> trainerTeam = requireTeam(
            context,
            teamService,
            trainer.get(),
            "teamname",
            "Please specify a team name to check!",
            "Team not found! Please specify a valid team name.");
        if (trainerTeam.isEmpty()) {
            return;
        }

        List<Pokemon> teamInfo = trainerTeam.get().getTeamAsList();
        if (teamInfo == null || teamInfo.isEmpty()) {
            reply(context, "Your team is currently empty!");
            return;
        }

        StringBuilder teamMessage = new StringBuilder("Your current team:\n");
        int slotNumber = 1;
        for (Pokemon pokemon : teamInfo) {
            teamMessage.append("Slot ").append(slotNumber).append(":\n");
            teamMessage.append("- ").append(pokemon.getNickname()).append("\n");
            teamMessage.append("  Species: ").append(pokemon.getSpecies().getDisplayName()).append("\n");
            teamMessage.append("  Level: ").append(pokemon.getLevel()).append("\n");
            LOGGER.info(
                "Current HP for Pokemon in slot {} is {} for Pokemon with instance_id {} in trainer ID {}'s team.",
                slotNumber,
                pokemon.getCurrentHP(),
                pokemon.getPokemonDbId(),
                trainer.get().getTrainerDbId());
            teamMessage.append("  HP: ").append(pokemon.getCurrentHP()).append('/').append(pokemon.getMaxHP()).append("\n");
            teamMessage.append("  Attack: ").append(pokemon.getCurrentAttack()).append("\n");
            teamMessage.append("  Defense: ").append(pokemon.getCurrentDefense()).append("\n");
            teamMessage.append("  Special Attack: ").append(pokemon.getCurrentSpecialAttack()).append("\n");
            teamMessage.append("  Special Defense: ").append(pokemon.getCurrentSpecialDefense()).append("\n");
            teamMessage.append("  Speed: ").append(pokemon.getCurrentSpeed()).append("\n\n");
            slotNumber++;
        }

        reply(context, teamMessage.toString());
    }
}