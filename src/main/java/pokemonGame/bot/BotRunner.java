package pokemonGame.bot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class BotRunner {
    public static void main( String[] args ) {
        String token = System.getenv("MOKEPONS_API_KEY");

        JDA api = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new PingPong())
                .addEventListeners(new SlashExample())
                .build();

            // Register the say slash command with Discord
            api.updateCommands()
                .addCommands(Commands.slash("say", "Says what you tell it to")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "content", "What the bot should say", true, true))
                .addCommands(Commands.slash("ping", "Pings the bot")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL))
                .addCommands(Commands.slash("battlestate", "Shows the current state of a battle")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL))
                .queue();

    }

    
}
