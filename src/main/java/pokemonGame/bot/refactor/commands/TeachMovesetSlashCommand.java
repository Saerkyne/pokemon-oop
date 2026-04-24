package pokemonGame.bot.refactor.commands;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.bot.refactor.SlashCommandContext;
import pokemonGame.bot.refactor.SlashCommandHandler;
import pokemonGame.bot.refactor.SlashCommandSupport;
import pokemonGame.model.LearnsetEntry;
import pokemonGame.model.Move;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.model.Trainer;
import pokemonGame.service.MoveSlotService;
import pokemonGame.service.TeamService;
import pokemonGame.service.TrainerService;

/**
 * Router-based version of the teachmoveset command.
 * Shows how shared trainer/team/Pokemon helpers reduce repeated lookup code.
 */
public final class TeachMovesetSlashCommand extends SlashCommandSupport implements SlashCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeachMovesetSlashCommand.class);
    private static final String MISSING_TEAM_OR_POKEMON_MESSAGE =
        "Please specify a team name and the nickname of the Pokémon you want to teach the move to!";

    private final MoveSlotService moveSlotService;
    private final TeamService teamService;
    private final TrainerService trainerService;

    public TeachMovesetSlashCommand(
        MoveSlotService moveSlotService,
        TeamService teamService,
        TrainerService trainerService) {
        this.moveSlotService = Objects.requireNonNull(moveSlotService);
        this.teamService = Objects.requireNonNull(teamService);
        this.trainerService = Objects.requireNonNull(trainerService);
    }

    @Override
    public String commandName() {
        return "teachmoveset";
    }

    @Override
    public void handle(SlashCommandContext context) {
        LOGGER.info(
            "Received slash command; '{}' from user: {} (ID: {})",
            context.commandName(),
            context.user().getName(),
            context.userId());

        Optional<Trainer> trainer = requireTrainer(
            context,
            trainerService,
            "You need to create a trainer first using /createtrainer!");
        if (trainer.isEmpty()) {
            return;
        }

        Optional<Team> team = requireTeam(
            context,
            teamService,
            trainer.get(),
            "teamname",
            MISSING_TEAM_OR_POKEMON_MESSAGE,
            "Team not found! Please specify a valid team name.");
        if (team.isEmpty()) {
            return;
        }

        Optional<Pokemon> selectedPokemon = requirePokemon(
            context,
            teamService,
            trainer.get(),
            team.get(),
            "pokemon",
            MISSING_TEAM_OR_POKEMON_MESSAGE,
            "No Pokémon with that nickname found on your team! Make sure you entered the correct nickname.");
        if (selectedPokemon.isEmpty()) {
            return;
        }

        List<String> moveNames = Stream.of("moveone", "movetwo", "movethree", "movefour")
            .map(name -> context.event().getOption(name))
            .filter(Objects::nonNull)
            .map(option -> option.getAsString())
            .distinct()
            .toList();

        for (String moveName : moveNames) {
            Move moveToTeach = moveSlotService.getMoveByName(moveName);
            if (moveToTeach == null) {
                replyEphemeral(
                    context,
                    "Move '" + moveName + "' not found! Please make sure you entered the move name correctly.");
                return;
            }

            if (!isEligibleMove(selectedPokemon.get(), moveName)) {
                replyEphemeral(
                    context,
                    "Move '" + moveName + "' is not in " + selectedPokemon.get().getNickname() + "'s learnset!");
                return;
            }

            if (!meetsLevelRequirement(selectedPokemon.get(), moveName)) {
                replyEphemeral(
                    context,
                    "Your Pokémon doesn't meet the level requirement to learn " + moveName + "!");
                return;
            }

            moveSlotService.teachMove(selectedPokemon.get(), moveToTeach);
        }

        reply(context, "Successfully taught " + moveNames.size() + " moves to " + selectedPokemon.get().getNickname() + "!");
    }

    private boolean isEligibleMove(Pokemon pokemon, String moveName) {
        return moveSlotService.getEligibleMoves(pokemon).stream()
            .map(LearnsetEntry::getMove)
            .map(Move::getMoveName)
            .anyMatch(eligibleMoveName -> eligibleMoveName.equalsIgnoreCase(moveName));
    }

    private boolean meetsLevelRequirement(Pokemon pokemon, String moveName) {
        for (LearnsetEntry entry : moveSlotService.getEligibleMoves(pokemon)) {
            if (!entry.getMove().getMoveName().equalsIgnoreCase(moveName)) {
                continue;
            }
            if (entry.getSource() == LearnsetEntry.Source.LEVEL && entry.getParameter() > pokemon.getLevel()) {
                return false;
            }
            return true;
        }

        return true;
    }
}