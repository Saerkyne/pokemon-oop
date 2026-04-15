package pokemonGame.bot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import pokemonGame.db.BattleCRUD;
import pokemonGame.db.MoveCRUD;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import pokemonGame.service.BattleService;
import pokemonGame.service.MoveSlotService;

// mvn compile exec:java -Dexec.mainClass="pokemonGame.bot.BotRunner"


public class BotRunner {
    public static void main( String[] args ) {
        String token = System.getenv("MOKEPONS_API_KEY");
        BattleService battleService = new BattleService(new BattleCRUD());
        MoveSlotService moveSlotService = new MoveSlotService(new MoveCRUD());


        JDA api = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new SlashExample(battleService, moveSlotService))
                .addEventListeners(new AutoCompleteBot())
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
                
                .addCommands(Commands.slash("checkteam", "Checks the trainer's team makeup")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "team", "Required, specify team name", true, true))
            
                .addCommands(Commands.slash("addpokemon", "Adds a Pokémon to your team")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "team", "The team to add the Pokémon to", true, true)
                    .addOption(OptionType.STRING, "species", "The name of the Pokémon to add (Gen 1 only currently)", true, true)
                    .addOption(OptionType.STRING, "nickname", "The nickname for the Pokémon (optional)", false))
        
                .addCommands(Commands.slash("releasepokemon", "Releases a Pokémon from your team")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "team", "The team the Pokémon belongs to", true, true)
                    .addOption(OptionType.STRING, "pokemon", "The nickname (or species, if not nicknamed) of the Pokémon to release", true, true))
    
                .addCommands(Commands.slash("cleardatabase", "Clears all data from the database (trainers, teams, and Pokémon)")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                    .addOption(OptionType.STRING, "confirm", "Type 'CONFIRM' to clear the database", true))

                .addCommands(Commands.slash("startbattle", "Starts a battle with another trainer (not implemented yet)")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "team", "The team you want to battle with", true, true)
                    .addOption(OptionType.USER, "opponent", "The trainer you want to battle", true))

                .addCommands(Commands.slash("teachmoveset", "Teaches a move from the Pokémon's learnset (Gen 1 only currently, up to 4 moves). This will overwrite the Pokémon's existing moveset, so be careful!")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "team", "The team the Pokémon belongs to", true, true)
                    .addOption(OptionType.STRING, "pokemon", "The nickname (or species, if not nicknamed) of the Pokémon to teach the move to", true, true)
                    .addOption(OptionType.STRING, "move one", "The name of the move to teach", true, true)
                    .addOption(OptionType.STRING, "move two", "The name of the move to teach", false, true)
                    .addOption(OptionType.STRING, "move three", "The name of the move to teach", false, true)
                    .addOption(OptionType.STRING, "move four", "The name of the move to teach", false, true))
                
                .addCommands(Commands.slash("createteam", "Creates a new team for your trainer")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "teamname", "The name of the team to create", true, true))
                .queue();

    }

    
    
}
