package pokemonGame.bot.refactor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pokemonGame.bot.refactor.commands.CreateTrainerSlashCommand;
import pokemonGame.bot.refactor.commands.CreateTeamSlashCommand;
import pokemonGame.bot.refactor.commands.TeachMovesetSlashCommand;
import pokemonGame.service.BattleService;
import pokemonGame.service.MoveSlotService;
import pokemonGame.service.TeamService;
import pokemonGame.service.TrainerService;

/**
 * Parallel listener that shows router-based command setup beside SlashExample.
 * Leave unregistered until more commands migrate and comparison is done.
 */
public class CommandRouter extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRouter.class);

    private final Map<String, SlashCommandHandler> handlers;

    public CommandRouter(
        BattleService battleService,
        MoveSlotService moveSlotService,
        TrainerService trainerService,
        TeamService teamService) {
        Objects.requireNonNull(battleService);
        Objects.requireNonNull(moveSlotService);
        Objects.requireNonNull(trainerService);
        Objects.requireNonNull(teamService);

        Map<String, SlashCommandHandler> handlerMap = new LinkedHashMap<>();
        register(handlerMap, new CreateTrainerSlashCommand(trainerService));
        register(handlerMap, new TeachMovesetSlashCommand(moveSlotService, teamService, trainerService));
        register(handlerMap, new CreateTeamSlashCommand(teamService, trainerService));
        // Next migrations live here, one command per class.
        // register(handlerMap, new AddPokemonSlashCommand(trainerService, teamService));
        // register(handlerMap, new StartBattleSlashCommand(trainerService, teamService, battleService));

        this.handlers = Map.copyOf(handlerMap);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        SlashCommandContext context = SlashCommandContext.from(event);
        SlashCommandHandler handler = handlers.get(context.commandName());

        if (handler == null) {
            event.reply("I can't handle that command right now :(")
                .setEphemeral(true)
                .queue();
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