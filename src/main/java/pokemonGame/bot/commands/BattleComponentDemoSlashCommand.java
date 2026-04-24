package pokemonGame.bot.commands;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.components.actionrow.ActionRow;
import pokemonGame.bot.BattleComponentListenerExample;
import pokemonGame.bot.SlashCommandContext;
import pokemonGame.bot.SlashCommandHandler;
import pokemonGame.bot.SlashCommandSupport;

/**
 * Temporary slash command that posts sample battle components.
 *
 * <p>Use this to test button/select-menu wiring before real battle-service calls
 * exist. Pattern stays same as production flow:</p>
 * <ul>
 *   <li>slash command emits message components</li>
 *   <li>component listener receives clicks</li>
 *   <li>listener validates owner and routes action</li>
 * </ul>
 *
 * <p>Current message is ephemeral for safe testing. If you need to verify wrong-user
 * rejection with a second Discord account, temporarily remove {@code setEphemeral(true)}.</p>
 */
public final class BattleComponentDemoSlashCommand extends SlashCommandSupport implements SlashCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleComponentDemoSlashCommand.class);
    private static final long DEMO_BATTLE_ID = 42L;

    private final BattleComponentListenerExample componentExample;

    public BattleComponentDemoSlashCommand(BattleComponentListenerExample componentExample) {
        this.componentExample = Objects.requireNonNull(componentExample);
    }

    @Override
    public String commandName() {
        return "battlecomponentdemo";
    }

    @Override
    public void handle(SlashCommandContext context) {
        LOGGER.info(
            "Received slash command; '{}' from user: {} (ID: {})",
            context.commandName(),
            context.user().getName(),
            context.userId());

        ActionRow challengeRow = componentExample.createChallengeRow(DEMO_BATTLE_ID, context.userId());
        ActionRow moveRow = componentExample.createMoveRow(
            DEMO_BATTLE_ID,
            context.userId(),
            List.of("Thunderbolt", "Quick Attack", "Tail Whip", "Thunder Wave"));
        ActionRow switchRow = ActionRow.of(componentExample.createSwitchMenu(
            DEMO_BATTLE_ID,
            context.userId(),
            List.of(
                new BattleComponentListenerExample.SwitchChoice("Pikachu", "Slot 0 | HP 30/35", 0),
                new BattleComponentListenerExample.SwitchChoice("Bulbasaur", "Slot 1 | HP 18/20", 1),
                new BattleComponentListenerExample.SwitchChoice("Squirtle", "Slot 2 | HP 25/25", 2))));

        context.event().reply(
            "Battle component demo. Click buttons or pick from menu to exercise component listener callbacks.")
            .addComponents(challengeRow, moveRow, switchRow)
            .setEphemeral(true)
            .queue();
    }
}