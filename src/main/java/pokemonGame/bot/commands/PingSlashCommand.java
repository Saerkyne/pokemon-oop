package pokemonGame.bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.bot.SlashCommandContext;
import pokemonGame.bot.SlashCommandHandler;
import pokemonGame.bot.SlashCommandSupport;

public final class PingSlashCommand extends SlashCommandSupport implements SlashCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingSlashCommand.class);

    @Override
    public String commandName() {
        return "ping";
    }

    @Override
    public void handle(SlashCommandContext context) {
        LOGGER.info(
            "Received slash command; '{}' from user: {} (ID: {})",
            context.commandName(),
            context.user().getName(),
            context.userId());

        reply(context, "Pong!");
    }
}