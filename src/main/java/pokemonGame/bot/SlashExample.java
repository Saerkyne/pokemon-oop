package pokemonGame.bot;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SlashExample extends ListenerAdapter{

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) {
            return;
        }

        switch (event.getName()) {
            case "say":
                say(event, event.getOption("content").getAsString());
                break;
            case "ping":
                event.reply("Pong!").queue();
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
