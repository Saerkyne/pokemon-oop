package pokemonGame.bot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pokemonGame.db.TeamCRUD;
import pokemonGame.db.TrainerCRUD;
import pokemonGame.model.Team;
import pokemonGame.model.Trainer;
import pokemonGame.species.PokemonFactory;

import java.util.Collections;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

public class AutoCompleteBot extends ListenerAdapter {

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {

        String commandName = event.getName();
        if (commandName == null) {
            return; // No command name, can't handle autocomplete
        }
        switch (commandName) {
            case "addpokemon":
                handleAddPokemonAutoComplete(event);
                break;
            case "releasepokemon":
                handleReleasePokemonAutoComplete(event);
                break;
            case "checkteam":
                handleCheckTeamAutoComplete(event);
                break;
            default:
                // No autocomplete for other commands
                break;
        }
    }


    private void handleAddPokemonAutoComplete(CommandAutoCompleteInteractionEvent event) {
        String focusedAddPokemon = event.getFocusedOption().getName();
        if (focusedAddPokemon == null) {
            return; // No focused option, can't handle autocomplete
        }
        if (focusedAddPokemon.equals("species")) {
            String userInput = event.getFocusedOption().getValue().toLowerCase();
        
            List<Command.Choice> options = PokemonFactory.getSpeciesNames().stream()
                    .filter(name -> name.toLowerCase().startsWith(userInput))
                    .sorted() // Alphabetical order
                    .limit(25) // Discord allows a maximum of 25 autocomplete options
                    .map(name -> new Command.Choice(name, name)) // Use the species name as both the display and value
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }

    private void handleReleasePokemonAutoComplete(CommandAutoCompleteInteractionEvent event) {
        String focusedReleasePokemon = event.getFocusedOption().getName();
        String focusedTeam = null;
        if (focusedReleasePokemon == null) {
            return; // No focused option, can't handle autocomplete
        }
        if (focusedReleasePokemon.equals("team")) {
            focusedTeam = "team";
            String userInput = event.getFocusedOption().getValue().toLowerCase();
            Long discordId = event.getUser().getIdLong();
            TrainerCRUD trainerCRUD = new TrainerCRUD();
            TeamCRUD teamCRUD = new TeamCRUD();
            Trainer trainer = trainerCRUD.getTrainerByDiscordId(discordId);
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                return;
            }
            
            List<Command.Choice> options = teamCRUD.getTeamNamesForTrainer(trainer.getTrainerDbId()).stream()
                .filter(teamName -> teamName.toLowerCase().startsWith(userInput))
                .map(teamName -> new Command.Choice(teamName, teamName))
                .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }

        if (focusedReleasePokemon.equals("pokemon") && focusedTeam == null) {
            // If the user is trying to autocomplete the "pokemon" option but hasn't selected a team yet,
            // we can't provide any valid options, so we return an empty list.
            event.replyChoices(Collections.emptyList()).queue();
            return;
        }

        if (focusedReleasePokemon.equals("pokemon") && event.getOption("team") != null) {
            // If the user has selected a team but hasn't started typing the pokemon name yet,
            // we can provide a list of all pokemon on that team as autocomplete options.
            String teamName = Optional.ofNullable(event.getOption("team"))
                .map(option -> option.getAsString())
                .orElse(null);
            Long discordId = event.getUser().getIdLong();
            TrainerCRUD trainerCRUD = new TrainerCRUD();
            TeamCRUD teamCRUD = new TeamCRUD();
            Trainer trainer = trainerCRUD.getTrainerByDiscordId(discordId);
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                return;
            }
            Team selectedTeam = trainer.getTeam(teamName);
            if (selectedTeam == null) {
                event.replyChoices(Collections.emptyList()).queue(); // Team not found on trainer, return empty choices
                return;
            }
            Team trainerTeam = teamCRUD.getDBTeamForTrainer(trainer.getTrainerDbId(), selectedTeam.getTeamDbId());
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
            TrainerCRUD trainerCRUD = new TrainerCRUD();
            TeamCRUD teamCRUD = new TeamCRUD();
            Trainer trainer = trainerCRUD.getTrainerByDiscordId(discordId);
            if (trainer == null) {
                event.replyChoices(Collections.emptyList()).queue(); // No trainer found, return empty choices
                return;
            }
            
            List<Command.Choice> options = teamCRUD.getTeamNamesForTrainer(trainer.getTrainerDbId()).stream()
                .filter(teamName -> teamName.toLowerCase().startsWith(userInput))
                .map(teamName -> new Command.Choice(teamName, teamName))
                .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }
}
