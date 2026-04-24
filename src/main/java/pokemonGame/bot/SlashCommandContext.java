package pokemonGame.bot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * Shared event data passed to routed slash command handlers.
 */
public record SlashCommandContext(SlashCommandInteractionEvent event, User user, long userId) {

    public static SlashCommandContext from(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        return new SlashCommandContext(event, user, user.getIdLong());
    }

    public String commandName() {
        return event.getName();
    }
}