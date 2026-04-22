package pokemonGame.bot.refactor;

/**
 * Handles one slash command in router-based bot setup.
 */
public interface SlashCommandHandler {

    String commandName();

    void handle(SlashCommandContext context);
}