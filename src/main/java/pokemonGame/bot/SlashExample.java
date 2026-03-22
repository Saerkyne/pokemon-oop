package pokemonGame.bot;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.util.logging.Logger;

public class SlashExample extends ListenerAdapter{

    private static final Logger LOGGER = Logger.getLogger(SlashExample.class.getName());

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) {
            return;
        }
        String user = event.getUser().getName();
        long userId = event.getUser().getIdLong();



        switch (event.getName()) {
            case "say":
                LOGGER.log(java.util.logging.Level.INFO, "Received slash command: '" + event.getName() + "' with content: '" + event.getOption("content").getAsString() + "' from user: " + user + " (ID: " + userId + ")");
                say(event, event.getOption("content").getAsString());
                break;

            case "ping":
                LOGGER.log(java.util.logging.Level.INFO, "Received slash command: '" + event.getName() + "' from user: " + user + " (ID: " + userId + ")");
                event.reply("Pong!").queue();
                break;
            
            case "battlestate":
                LOGGER.log(java.util.logging.Level.INFO, "Received slash command: '" + event.getName() + "' from user: " + user + " (ID: " + userId + ")");
                event.reply("The battle is currently in progress!").queue();
                break;
            
            default:
                event.reply("I can't handle that command right now :(")
                    .setEphemeral(true)
                    .queue();  
        }
    }


    public void say(SlashCommandInteractionEvent event, String content) {
        event.reply(content).queue();
    }
}
