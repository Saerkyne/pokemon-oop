package pokemonGame.bot.refactor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Example JDA component listener for battle interactions.
 *
 * <p>Purpose: show how battle UI can move from slash-command-only input to
 * message components without putting battle rules in event handlers.</p>
 *
 * <p>Pattern:</p>
 * <ul>
 *   <li>Encode battleId, ownerDiscordId, and slot info into component IDs</li>
 *   <li>Reject clicks from wrong user before touching service/database code</li>
 *   <li>Translate click into simple data, then hand off to BattleService</li>
 * </ul>
 *
 * <p>This class is intentionally not wired into {@code BotRunner} yet. It is a
 * compile-clean reference file for the next bot-layer slice.</p>
 */
public class BattleComponentListenerExample extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleComponentListenerExample.class);
    private static final String PREFIX = "battle";
    private static final int UNUSED_SLOT = -1;

    /**
     * Builds Accept/Decline buttons for a pending challenge.
     */
    public ActionRow createChallengeRow(long battleId, long challengedUserId) {
        return ActionRow.of(
            Button.success(componentId(Action.ACCEPT, battleId, challengedUserId, UNUSED_SLOT), "Accept"),
            Button.danger(componentId(Action.DECLINE, battleId, challengedUserId, UNUSED_SLOT), "Decline")
        );
    }

    /**
     * Builds up to four move buttons for active battle turn input.
     */
    public ActionRow createMoveRow(long battleId, long ownerDiscordId, List<String> moveNames) {
        Objects.requireNonNull(moveNames, "moveNames");
        if (moveNames.isEmpty()) {
            throw new IllegalArgumentException("moveNames must contain at least one move");
        }
        if (moveNames.size() > 5) {
            throw new IllegalArgumentException("Discord button rows support at most five buttons");
        }

        List<Button> buttons = new ArrayList<>();
        for (int slotIndex = 0; slotIndex < moveNames.size(); slotIndex++) {
            String moveName = moveNames.get(slotIndex);
            buttons.add(Button.primary(
                componentId(Action.MOVE, battleId, ownerDiscordId, slotIndex),
                moveName));
        }

        return ActionRow.of(buttons);
    }

    /**
     * Builds a select menu for lead choice or forced switch.
     */
    public StringSelectMenu createSwitchMenu(long battleId, long ownerDiscordId, List<SwitchChoice> choices) {
        Objects.requireNonNull(choices, "choices");
        if (choices.isEmpty()) {
            throw new IllegalArgumentException("choices must contain at least one switch option");
        }

        StringSelectMenu.Builder builder = StringSelectMenu.create(
            componentId(Action.SWITCH, battleId, ownerDiscordId, UNUSED_SLOT))
            .setPlaceholder("Pick a Pokemon")
            .setRequiredRange(1, 1);

        for (SwitchChoice choice : choices) {
            builder.addOption(
                choice.label(),
                Integer.toString(choice.teamSlot()),
                choice.description());
        }

        return builder.build();
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Optional<BattleComponentId> parsedId = BattleComponentId.parse(event.getComponentId());
        if (parsedId.isEmpty()) {
            return;
        }

        BattleComponentId componentId = parsedId.get();
        if (!ownsComponent(event.getUser().getIdLong(), componentId.ownerDiscordId())) {
            event.reply("These controls belong to another trainer.").setEphemeral(true).queue();
            return;
        }

        LOGGER.info(
            "Battle button click: action={}, battleId={}, ownerDiscordId={}, slotIndex={}, clickerDiscordId={}",
            componentId.action(),
            componentId.battleId(),
            componentId.ownerDiscordId(),
            componentId.slotIndex(),
            event.getUser().getIdLong());

        switch (componentId.action()) {
            case ACCEPT -> event.reply(
                "Accepted challenge for battle " + componentId.battleId()
                    + ". Next step: call BattleService.acceptChallenge() and send lead select menu.")
                .queue();
            case DECLINE -> event.reply(
                "Declined challenge for battle " + componentId.battleId()
                    + ". Next step: mark battle finished/cancelled and notify challenger.")
                .setEphemeral(true)
                .queue();
            case MOVE -> event.reply(
                "Locked move slot " + componentId.slotIndex() + " for battle " + componentId.battleId()
                    + ". Next step: call BattleService.submitAction() with MoveAction.")
                .setEphemeral(true)
                .queue();
            case FORFEIT -> event.reply(
                "Forfeit clicked for battle " + componentId.battleId()
                    + ". Next step: set winner and close battle.")
                .setEphemeral(true)
                .queue();
            case SWITCH -> event.reply("Switch choice uses select menu in this example.")
                .setEphemeral(true)
                .queue();
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        Optional<BattleComponentId> parsedId = BattleComponentId.parse(event.getComponentId());
        if (parsedId.isEmpty()) {
            return;
        }

        BattleComponentId componentId = parsedId.get();
        if (componentId.action() != Action.SWITCH) {
            return;
        }

        if (!ownsComponent(event.getUser().getIdLong(), componentId.ownerDiscordId())) {
            event.reply("These controls belong to another trainer.").setEphemeral(true).queue();
            return;
        }

        List<String> values = event.getValues();
        if (values.isEmpty()) {
            event.reply("No Pokemon was selected.").setEphemeral(true).queue();
            return;
        }

        int selectedTeamSlot;
        try {
            selectedTeamSlot = Integer.parseInt(values.get(0));
        } catch (NumberFormatException exception) {
            event.reply("Invalid switch selection received.").setEphemeral(true).queue();
            return;
        }

        LOGGER.info(
            "Battle select menu click: battleId={}, ownerDiscordId={}, selectedTeamSlot={}, clickerDiscordId={}",
            componentId.battleId(),
            componentId.ownerDiscordId(),
            selectedTeamSlot,
            event.getUser().getIdLong());

        event.reply(
            "Selected team slot " + selectedTeamSlot + " for battle " + componentId.battleId()
                + ". Next step: call BattleService.setLead() or submitAction() with SwitchAction.")
            .setEphemeral(true)
            .queue();
    }

    private static boolean ownsComponent(long clickingDiscordId, long ownerDiscordId) {
        return clickingDiscordId == ownerDiscordId;
    }

    private static String componentId(Action action, long battleId, long ownerDiscordId, int slotIndex) {
        return PREFIX + ':' + action.name() + ':' + battleId + ':' + ownerDiscordId + ':' + slotIndex;
    }

    private enum Action {
        ACCEPT,
        DECLINE,
        MOVE,
        SWITCH,
        FORFEIT
    }

    /**
     * Small DTO for dynamic switch menu options.
     */
    public record SwitchChoice(String label, String description, int teamSlot) {

        public SwitchChoice {
            Objects.requireNonNull(label, "label");
            Objects.requireNonNull(description, "description");
        }
    }

    /**
     * Parsed custom ID for battle components.
     */
    private record BattleComponentId(Action action, long battleId, long ownerDiscordId, int slotIndex) {

        private static Optional<BattleComponentId> parse(String rawComponentId) {
            if (rawComponentId == null || rawComponentId.isBlank()) {
                return Optional.empty();
            }

            String[] parts = rawComponentId.split(":", -1);
            if (parts.length != 5 || !PREFIX.equals(parts[0])) {
                return Optional.empty();
            }

            try {
                Action action = Action.valueOf(parts[1]);
                long battleId = Long.parseLong(parts[2]);
                long ownerDiscordId = Long.parseLong(parts[3]);
                int slotIndex = Integer.parseInt(parts[4]);
                return Optional.of(new BattleComponentId(action, battleId, ownerDiscordId, slotIndex));
            } catch (IllegalArgumentException exception) {
                LOGGER.debug("Ignoring malformed battle component ID: {}", rawComponentId, exception);
                return Optional.empty();
            }
        }
    }
}