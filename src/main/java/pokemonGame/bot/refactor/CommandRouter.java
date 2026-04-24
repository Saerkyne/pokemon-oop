package pokemonGame.bot.refactor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pokemonGame.bot.refactor.commands.CreateTrainerSlashCommand;
import pokemonGame.bot.refactor.commands.AddPokemonSlashCommand;
import pokemonGame.bot.refactor.commands.BattleStateSlashCommand;
import pokemonGame.bot.refactor.commands.BattleComponentDemoSlashCommand;
import pokemonGame.bot.refactor.commands.CheckTeamSlashCommand;
import pokemonGame.bot.refactor.commands.ClearDatabaseSlashCommand;
import pokemonGame.bot.refactor.commands.CreateTeamSlashCommand;
import pokemonGame.bot.refactor.commands.PingSlashCommand;
import pokemonGame.bot.refactor.commands.ReleasePokemonSlashCommand;
import pokemonGame.bot.refactor.commands.TeachMovesetSlashCommand;
import pokemonGame.bot.refactor.commands.StartBattleSlashCommand;
import pokemonGame.service.BattleService;
import pokemonGame.service.MoveSlotService;
import pokemonGame.service.TeamService;
import pokemonGame.service.TrainerService;
import pokemonGame.service.PokemonService;

/**
 * Primary slash-command router.
 * One listener owns slash command dispatch so each interaction is replied to once.
 */
public class CommandRouter extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRouter.class);

    private final Map<String, SlashCommandHandler> handlers;

    public CommandRouter(
        BattleService battleService,
        MoveSlotService moveSlotService,
        TrainerService trainerService,
        TeamService teamService,
        PokemonService pokemonService) {
        Objects.requireNonNull(battleService);
        Objects.requireNonNull(moveSlotService);
        Objects.requireNonNull(trainerService);
        Objects.requireNonNull(teamService);
        Objects.requireNonNull(pokemonService);

        Map<String, SlashCommandHandler> handlerMap = new LinkedHashMap<>();
        register(handlerMap, new PingSlashCommand());
        register(handlerMap, new BattleStateSlashCommand());
        register(handlerMap, new CreateTrainerSlashCommand(trainerService));
        register(handlerMap, new TeachMovesetSlashCommand(moveSlotService, teamService, trainerService));
        register(handlerMap, new CreateTeamSlashCommand(teamService, trainerService));
        register(handlerMap, new CheckTeamSlashCommand(teamService, trainerService));
        register(handlerMap, new AddPokemonSlashCommand(teamService, trainerService, pokemonService));
        register(handlerMap, new ReleasePokemonSlashCommand(teamService, trainerService));
        register(handlerMap, new ClearDatabaseSlashCommand());
        register(handlerMap, new StartBattleSlashCommand(trainerService, teamService, battleService));
        register(handlerMap, new BattleComponentDemoSlashCommand(new BattleComponentListenerExample()));

        /*
         * Battle-command roadmap:
         * - Challenge handshake: AcceptChallenge/DeclineChallenge complete PENDING -> TEAM_SETUP.
         * - Battle visibility: CheckBattleStatus should expose opponent, leads, turn number, and pending-action state.
         * - Turn loop: TurnAction submits MoveAction/SwitchAction, but move and lead selection likely fit buttons/select menus better than slash input.
         * - Exit path: ForfeitBattle gives clean FINISHED transition when player leaves.
         * - Prep helpers below support battle setup, but they are not battle-lifecycle handlers.
         */
        // register(handlerMap, new RemovePokemonSlashCommand());
        // register(handlerMap, new AcceptChallengeSlashCommand());
        // register(handlerMap, new DeclineChallengeSlashCommand());
        // register(handlerMap, new CheckMovesetSlashCommand());
        // register(handlerMap, new ForfeitBattleSlashCommand());
        // register(handlerMap, new CheckBattleStatusSlashCommand());
        // register(handlerMap, new TurnActionSlashCommand());

        this.handlers = Map.copyOf(handlerMap);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        SlashCommandContext context = SlashCommandContext.from(event);
        SlashCommandHandler handler = handlers.get(context.commandName());

        if (handler == null) {
            return;
        }

        LOGGER.info(
            "Routing slash command; '{}' to {}",
            context.commandName(),
            handler.getClass().getSimpleName());
        handler.handle(context);
    }

    private void register(Map<String, SlashCommandHandler> handlerMap, SlashCommandHandler handler) {
        handlerMap.put(handler.commandName(), handler);
    }
}