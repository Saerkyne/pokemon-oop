package pokemonGame.bot.commands;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.bot.SlashCommandContext;
import pokemonGame.bot.SlashCommandHandler;
import pokemonGame.bot.SlashCommandSupport;
import pokemonGame.model.Trainer;
import pokemonGame.service.TrainerService;

/**
 * Router-based version of the createtrainer command.
 */
public final class CreateTrainerSlashCommand extends SlashCommandSupport implements SlashCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTrainerSlashCommand.class);

    private final TrainerService trainerService;

    public CreateTrainerSlashCommand(TrainerService trainerService) {
        this.trainerService = Objects.requireNonNull(trainerService);
    }

    @Override
    public String commandName() {
        return "createtrainer";
    }

    @Override
    public void handle(SlashCommandContext context) {
        Optional<String> trainerName = requireStringOption(
            context,
            "trainername",
            "Please specify a trainer name to create!");
        if (trainerName.isEmpty()) {
            return;
        }

        LOGGER.info(
            "Received slash command; '{}' with content: '{}' from user: {} (ID: {})",
            context.commandName(),
            trainerName.get(),
            context.user(),
            context.userId());

        Trainer createAttempt = trainerService.createTrainer(
            trainerName.get(),
            context.userId(),
            context.user().getName());

        if (createAttempt == null) {
            replyEphemeral(context, "A trainer already exists for your Discord account!");
            return;
        }

        reply(context, "Trainer created successfully! You can now create teams with /createteam.");
    }
}