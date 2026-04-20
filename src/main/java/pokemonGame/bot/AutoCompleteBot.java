package pokemonGame.bot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.model.Team;
import pokemonGame.model.Trainer;
import pokemonGame.model.LearnsetEntry;
import pokemonGame.model.Move;
import pokemonGame.service.MoveSlotService;
import pokemonGame.service.TrainerService;
import pokemonGame.service.TeamService;
import pokemonGame.model.Pokemon;
import pokemonGame.species.PokeSpecies;


import java.util.Collections;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

public class AutoCompleteBot extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoCompleteBot.class);

    private final TrainerService trainerService;
    private final TeamService teamService;
    private final MoveSlotService moveSlotService;

    public AutoCompleteBot(TrainerService trainerService, TeamService teamService, MoveSlotService moveSlotService) {
        this.trainerService = trainerService;
        this.teamService = teamService;
        this.moveSlotService = moveSlotService;
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {

        String commandName = event.getName();
        switch (commandName) {
            case "addpokemon":
                try {
                    handleAddPokemonAutoComplete(event);
                } catch (Exception e) {
                    event.replyChoices(Collections.emptyList()).queue(); // In case of any exceptions, return empty choices
                    LOGGER.error("Error handling autocomplete for addpokemon command", e);
                }
                break;
            case "releasepokemon":
                try {
                    handleReleasePokemonAutoComplete(event);
                } catch (Exception e) {
                    event.replyChoices(Collections.emptyList()).queue(); // In case of any exceptions, return empty choices
                    LOGGER.error("Error handling autocomplete for releasepokemon command", e);
                }
                break;
            case "checkteam":
                try {
                    handleCheckTeamAutoComplete(event);
                } catch (Exception e) {
                    event.replyChoices(Collections.emptyList()).queue(); // In case of any exceptions, return empty choices
                    LOGGER.error("Error handling autocomplete for checkteam command", e);
                }
                break;
            case "teachmoveset":
                try {
                    handleTeachMovesetAutoComplete(event);
                } catch (Exception e) {
                    event.replyChoices(Collections.emptyList()).queue(); // In case of any exceptions, return empty choices
                    LOGGER.error("Error handling autocomplete for teachmoveset command", e);
                }
                break;
            case "startbattle":
                try {
                    handleStartBattleAutoComplete(event);
                } catch (Exception e) {
                    event.replyChoices(Collections.emptyList()).queue(); // In case of any exceptions, return empty choices
                    LOGGER.error("Error handling autocomplete for startbattle command", e);
                }
                break;
            default:
                // No autocomplete for other commands
                break;
        }
    }


    private void handleAddPokemonAutoComplete(CommandAutoCompleteInteractionEvent event) {
        String focusedAddPokemonTeam = event.getFocusedOption().getName();
        if (focusedAddPokemonTeam != null && focusedAddPokemonTeam.equals("team")) {
            String teamName = event.getFocusedOption().getValue().toLowerCase();
            Long discordId = event.getUser().getIdLong();
            Trainer trainer = trainerService.getTrainerByDiscordId(discordId);
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                return;
            }
            List<Command.Choice> teamOptions = teamService.getTeamNames(trainer.getTrainerDbId()).stream()
                .filter(name -> name.toLowerCase().startsWith(teamName))
                .sorted()
                .map(name -> new Command.Choice(name, name))
                .collect(Collectors.toList());
            event.replyChoices(teamOptions).queue();
            return;
        } else if (focusedAddPokemonTeam.equals("species")) {
            // If the user is trying to autocomplete the "species" option but hasn't selected a team yet,
            // we can still provide autocomplete options based on all available species.
            String userInput = event.getFocusedOption().getValue().toLowerCase();
        
            List<Command.Choice> options = PokeSpecies.getSpeciesNames().stream()
                    .filter(name -> name.toLowerCase().startsWith(userInput))
                    .sorted() // Alphabetical order
                    .limit(25) // Discord allows a maximum of 25 autocomplete options
                    .map(name -> new Command.Choice(name, name)) // Use the species name as both the display and value
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
            return;
        }
    }

    private void handleReleasePokemonAutoComplete(CommandAutoCompleteInteractionEvent event) {
        String focusedReleasePokemon = event.getFocusedOption().getName();
        if (focusedReleasePokemon == null) {
            return; // No focused option, can't handle autocomplete
        }
        if (focusedReleasePokemon.equals("team")) {
            String userInput = event.getFocusedOption().getValue().toLowerCase();
            Long discordId = event.getUser().getIdLong();
            Trainer trainer = trainerService.getTrainerByDiscordId(discordId);
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                return;
            }
            
            List<Command.Choice> options = teamService.getTeamNames(trainer.getTrainerDbId()).stream()
                .filter(teamName -> teamName.toLowerCase().startsWith(userInput))
                .sorted()
                .map(teamName -> new Command.Choice(teamName, teamName))
                .collect(Collectors.toList());
            event.replyChoices(options).queue();
        } else if (focusedReleasePokemon.equals("pokemon")) {
            if (event.getOption("team") == null) {
            // If the user is trying to autocomplete the "pokemon" option but hasn't selected a team yet,
            // we can't provide any valid options, so we return an empty list.
            event.replyChoices(Collections.emptyList()).queue();
            return;
            }
        
            String teamName = Optional.ofNullable(event.getOption("team"))
                .map(option -> option.getAsString())
                .orElse(null);
            Long discordId = event.getUser().getIdLong();
            Trainer trainer = trainerService.getTrainerByDiscordId(discordId);
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                return;
            }
            Team selectedTeam = trainer.getTeam(teamName);
            if (selectedTeam == null) {
                event.replyChoices(Collections.emptyList()).queue(); // Team not found on trainer, return empty choices
                return;
            }
            Team trainerTeam = teamService.loadTeam(trainer.getTrainerDbId(), selectedTeam.getTeamDbId());
            if (trainerTeam == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No team found, return empty choices
                return;
            }
            
            List<Command.Choice> options = trainerTeam.getTeamAsList().stream()
                .map(choice -> new Command.Choice(choice.getNickname(), choice.getNickname()))
                .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }

    private void handleCheckTeamAutoComplete(CommandAutoCompleteInteractionEvent event) {
        String focusedTeamCheck = event.getFocusedOption().getName();
        if (focusedTeamCheck == null) {
            return; // No focused option, can't handle autocomplete
        }

        if (focusedTeamCheck.equals("team")) {
            String userInput = event.getFocusedOption().getValue().toLowerCase();
            Long discordId = event.getUser().getIdLong();
            Trainer trainer = trainerService.getTrainerByDiscordId(discordId);
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                return;
            }
            
            List<Command.Choice> options = teamService.getTeamNames(trainer.getTrainerDbId()).stream()
                .filter(teamName -> teamName.toLowerCase().startsWith(userInput))
                .map(teamName -> new Command.Choice(teamName, teamName))
                .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }

    private void handleTeachMovesetAutoComplete(CommandAutoCompleteInteractionEvent event) {
       String focusedOptionName = event.getFocusedOption().getName();
        if (focusedOptionName == null) {
            return; // No focused option, can't handle autocomplete
        }
        
        if (focusedOptionName.equals("team")) {
            String focusedTeamCheck = event.getFocusedOption().getName();
            if (focusedTeamCheck == null) {
                return; // No focused option, can't handle autocomplete
            }

            if (focusedTeamCheck.equals("team")) {
                String userInput = event.getFocusedOption().getValue().toLowerCase();
                Long discordId = event.getUser().getIdLong();
                Trainer trainer = trainerService.getTrainerByDiscordId(discordId);
                if (trainer == null) {
                    event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                    return;
                }
                
                List<Command.Choice> options = teamService.getTeamNames(trainer.getTrainerDbId()).stream()
                    .filter(teamName -> teamName.toLowerCase().startsWith(userInput))
                    .map(teamName -> new Command.Choice(teamName, teamName))
                    .collect(Collectors.toList());
                event.replyChoices(options).queue();
            }
        } else if (focusedOptionName.equals("pokemon")) {
            if (event.getOption("team") == null) {
                // If the user is trying to autocomplete the "pokemon" option but hasn't selected a team yet,
                // we can't provide any valid options, so we return an empty list.
                event.replyChoices(Collections.emptyList()).queue();
                LOGGER.info("No team selected for Pokémon autocomplete");
                return; 
            }
            String teamName = Optional.ofNullable(event.getOption("team"))
                .map(option -> option.getAsString())
                .orElse(null);
            Long discordId = event.getUser().getIdLong();
            Trainer trainer = trainerService.getTrainerByDiscordId(discordId);
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                LOGGER.info("No trainer found for Pokémon autocomplete");
                return;
            }

            // TODO(review 2026-04-20): Null-check getTeamFromName(...) before dereferencing getTeamDbId().
            // Missing team names currently throw NullPointerException and get swallowed by broad catch blocks above.
            Team trainerTeam = teamService.loadTeam(trainer.getTrainerDbId(), teamService.getTeamFromName(trainer.getTrainerDbId(), teamName).getTeamDbId());
            if (trainerTeam == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No team found, return empty choices
                LOGGER.info("No DB team found for Pokémon autocomplete");
                return;
            }
            
            List<Command.Choice> options = trainerTeam.getTeamAsList().stream()
                .map(choice -> new Command.Choice(choice.getNickname(), choice.getNickname()))
                .collect(Collectors.toList());
            event.replyChoices(options).queue();

        } else if (focusedOptionName.contains("move")) {
            LOGGER.info("Providing move autocomplete options");
            if (event.getOption("pokemon") == null) {
                // If the user is trying to autocomplete a move option but hasn't selected a Pokémon yet,
                // we can't provide any valid options, so we return an empty list.
                event.replyChoices(Collections.emptyList()).queue();
                LOGGER.info("No Pokémon selected for move autocomplete");
                return;
            }
            if (event.getOption("team") == null) {
                // If the user is trying to autocomplete a move option but hasn't selected a team yet,
                // we can't provide any valid options, so we return an empty list.
                event.replyChoices(Collections.emptyList()).queue();
                LOGGER.info("No team selected for move autocomplete");
                return;
            }
            
            String userInput = event.getFocusedOption().getValue().toLowerCase();
            String pokemonName = event.getOption("pokemon").getAsString().toLowerCase();
            Trainer trainer = trainerService.getTrainerByDiscordId(event.getUser().getIdLong());
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                LOGGER.info("No trainer found for move autocomplete");

                return;
            }
            int trainerDbId = trainer.getTrainerDbId();

            // TODO(review 2026-04-20): Reuse a null-checked team lookup before loading DB team.
            // This chained dereference can still explode when autocomplete input references a deleted or renamed team.
            Team selectedTeam = teamService.loadTeam(trainerDbId, teamService.getTeamFromName(trainerDbId, event.getOption("team").getAsString()).getTeamDbId());
            if (selectedTeam == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No team found, return empty choices
                LOGGER.info("No DB team found for move autocomplete");
                return;
            }
            
            Pokemon selectedPokemon = teamService.getPokemonByNickname(trainerDbId, selectedTeam.getTeamDbId(), pokemonName);
            if (selectedPokemon == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No Pokémon found, return empty choices
                LOGGER.info("No DB Pokémon found for move autocomplete");
                return;
            }
            List<Command.Choice> options = moveSlotService.getEligibleMoves(selectedPokemon).stream()
                .map(LearnsetEntry::getMove)
                .map(Move::getMoveName)
                .filter(moveName -> moveName.toLowerCase().startsWith(userInput))
                .sorted() // Alphabetical order
                .distinct()
                .limit(25) // Discord allows a maximum of 25 autocomplete options
                .map(moveName -> new Command.Choice(moveName, moveName)) // Use the move name as both the display and value
                .collect(Collectors.toList());
            LOGGER.info("Providing move autocomplete options for Pokémon: {}", selectedPokemon.getNickname());
            LOGGER.info("Learnable moves: {}", options.stream().map(Command.Choice::getName).collect(Collectors.joining(", ")));
            event.replyChoices(options).queue();
        }
    }

    private void handleStartBattleAutoComplete(CommandAutoCompleteInteractionEvent event) {
        // TODO: Add option to autocomplete opponent trainer usernames from database once battle functionality is implemented
        String focusedOptionName = event.getFocusedOption().getName();
        if (focusedOptionName == null) {
            return; // No focused option, can't handle autocomplete
        }
        if (focusedOptionName.equals("team")) {
            String userInput = event.getFocusedOption().getValue().toLowerCase();
            Trainer trainer = trainerService.getTrainerByDiscordId(event.getUser().getIdLong());
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                return;
            }
            List<Command.Choice> options = teamService.getTeamNames(trainer.getTrainerDbId()).stream()
                .filter(teamName -> teamName.toLowerCase().startsWith(userInput))
                .sorted()
                .map(teamName -> new Command.Choice(teamName, teamName))
                .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }
}
