package pokemonGame.bot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;


public class BotRunner {
    public static void main( String[] args ) {
        String token = System.getenv("MOKEPONS_API_KEY");

        JDA api = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
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
                .addCommands(Commands.slash("createtrainer", "Creates a new trainer")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "name", "The name of the trainer", true, true))
                .addCommands(Commands.slash("checkteam", "Checks the trainer's current team")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL))
                .addCommands(Commands.slash("addpokemon", "Adds a Pokémon to your team")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "pokemon", "The name of the Pokémon to add (Gen 1 only currently)", true, true)
                    .addOption(OptionType.STRING, "nickname", "The nickname for the Pokémon (optional)", false, true))
                .addCommands(Commands.slash("releasepokemon", "Releases a Pokémon from your team")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.INTEGER, "slot", "The slot number of the Pokémon to release (1-6)", true, true))
                .addCommands(Commands.slash("cleardatabase", "Clears all data from the database (trainers, teams, and Pokémon)")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                    .addOption(OptionType.STRING, "confirm", "Type 'CONFIRM' to clear the database", true, true))
                .queue();

    }

    
}
