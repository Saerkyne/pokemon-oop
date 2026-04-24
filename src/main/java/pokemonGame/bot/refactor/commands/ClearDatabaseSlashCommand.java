package pokemonGame.bot.refactor.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import pokemonGame.bot.refactor.SlashCommandContext;
import pokemonGame.bot.refactor.SlashCommandHandler;
import pokemonGame.bot.refactor.SlashCommandSupport;
import pokemonGame.db.DatabaseSetup;

public final class ClearDatabaseSlashCommand extends SlashCommandSupport implements SlashCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClearDatabaseSlashCommand.class);

    @Override
    public String commandName() {
        return "cleardatabase";
    }

    @Override
    public void handle(SlashCommandContext context) {
        Member member = context.event().getMember();
        if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)) {
            reply(context, "You do not have permission to clear the database!");
            return;
        }

        String confirmation = context.event().getOption("confirm") == null
            ? null
            : context.event().getOption("confirm").getAsString();
        LOGGER.info(
            "Received slash command; '{}' with confirmation: '{}' from user: {} (ID: {})",
            context.commandName(),
            confirmation,
            context.user(),
            context.userId());

        if (confirmation != null && confirmation.equalsIgnoreCase("CONFIRM")) {
            DatabaseSetup.deleteAllData();
            reply(context, "Database cleared successfully!");
            return;
        }

        reply(context, "Database clear cancelled. To clear the database, you must type 'CONFIRM' in the confirmation option.");
    }
}