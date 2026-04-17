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
import pokemonGame.db.PokemonCRUD;
import pokemonGame.db.TeamCRUD;
import pokemonGame.db.TrainerCRUD;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import pokemonGame.service.BattleService;
import pokemonGame.service.MoveSlotService;
import pokemonGame.service.TeamService;
import pokemonGame.service.TrainerService;

// mvn compile exec:java -Dexec.mainClass="pokemonGame.bot.BotRunner"


public class BotRunner {
    public static void main( String[] args ) {
        
        String token = System.getenv("MOKEPONS_API_KEY");
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("MOKEPONS_API_KEY environment variable not set");
        }
        BattleService battleService = new BattleService(new BattleCRUD());
        MoveSlotService moveSlotService = new MoveSlotService(new MoveCRUD());
        TrainerService trainerService = new TrainerService(new TrainerCRUD());
        PokemonCRUD pokemonCRUD = new PokemonCRUD(moveSlotService);
        TeamService teamService = new TeamService(new TeamCRUD(), pokemonCRUD, trainerService);


        JDA api = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new SlashExample(battleService, moveSlotService, trainerService, teamService))
                .addEventListeners(new AutoCompleteBot(trainerService, teamService, moveSlotService))
                .build();

            // Register the say slash command with Discord
            api.updateCommands()
                .addCommands(Commands.slash("ping", "Pings the bot")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL))
                
                .addCommands(Commands.slash("battlestate", "Shows the current state of a battle")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL))
                
                .addCommands(Commands.slash("createtrainer", "Creates a new trainer")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "name", "The name of the trainer", true, false))
                
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

                .addCommands(Commands.slash("teachmoveset", "Teaches a moveset to a Pokemon on a team. Overwrites existing set.")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "team", "The team the Pokémon belongs to", true, true)
                    .addOption(OptionType.STRING, "pokemon", "The nickname (or species, if not nicknamed) of the Pokémon to teach the move to", true, true)
                    .addOption(OptionType.STRING, "moveone", "The name of the move to teach", true, true)
                    .addOption(OptionType.STRING, "movetwo", "The name of the move to teach", false, true)
                    .addOption(OptionType.STRING, "movethree", "The name of the move to teach", false, true)
                    .addOption(OptionType.STRING, "movefour", "The name of the move to teach", false, true))
                
                .addCommands(Commands.slash("createteam", "Creates a new team for your trainer")
                    .setContexts(InteractionContextType.ALL)
                    .setIntegrationTypes(IntegrationType.ALL)
                    .addOption(OptionType.STRING, "teamname", "The name of the team to create", true, false))
                .queue();

    }

    
    
}
