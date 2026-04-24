package pokemonGame.bot.refactor.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.bot.refactor.SlashCommandContext;
import pokemonGame.bot.refactor.SlashCommandHandler;
import pokemonGame.bot.refactor.SlashCommandSupport;

public final class BattleStateSlashCommand extends SlashCommandSupport implements SlashCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleStateSlashCommand.class);

    @Override
    public String commandName() {
        return "battlestate";
    }

    @Override
    public void handle(SlashCommandContext context) {
        LOGGER.info(
            "Received slash command; '{}' from user: {} (ID: {})",
            context.commandName(),
            context.user().getName(),
            context.userId());

        reply(context, "The battle is currently in progress!");
    }
}