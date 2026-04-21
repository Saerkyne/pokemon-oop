package pokemonGame.bot;
import pokemonGame.db.DatabaseSetup;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.model.Trainer;
import pokemonGame.service.BattleService;
import pokemonGame.service.MoveSlotService;
import pokemonGame.service.TrainerService;
import pokemonGame.species.PokeSpecies;
import pokemonGame.species.PokemonFactory;
import pokemonGame.core.StatCalculator;
import pokemonGame.service.TeamService;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import pokemonGame.model.Move;
import pokemonGame.model.LearnsetEntry;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlashExample extends ListenerAdapter{

    private static final Logger LOGGER = LoggerFactory.getLogger(SlashExample.class);

    private final BattleService battleService;
    private final MoveSlotService moveSlotService;
    private final TrainerService trainerService;
    private final TeamService teamService;
    
    public SlashExample(BattleService battleService, MoveSlotService moveSlotService, TrainerService trainerService, TeamService teamService) {
        this.battleService = battleService;
        this.moveSlotService = moveSlotService;
        this.trainerService = trainerService;
        this.teamService = teamService;
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        long userId = user.getIdLong();
        String eventName = event.getName();
        // TODO(review 2026-04-20): Split command routing into per-command handlers plus shared lookup/context helpers.
        // This listener still owns dispatch plus repeated trainer/team/Pokemon validation, which makes new commands and unit tests expensive.
        LOGGER.info("Received slash command; '{}' from user: {} (ID: {})", eventName, user, userId  );



        switch (eventName) {
            
            case "battlestate"-> {
                // Placeholder, this needs a handler function for pulling data from the database
                LOGGER.info("Received slash command; '{}' from user: {} (ID: {})", eventName, user, userId);
                event.reply("The battle is currently in progress!").queue();
                return;
            }

            case "startbattle" -> {
                handleStartBattle(event, user, userId);
                return;
            }
                
            
            case "createtrainer" -> {
                handleCreateTrainer(event, user, userId);
                return;
            }
            
            case "createteam" -> {
                handleCreateTeam(event, user, userId);
                return;
            }
            
            case "checkteam" -> {
                handleCheckTeam(event, user, userId);
                return;
            }

            case "addpokemon" -> {
                handleAddPokemon(event, user, userId);
                return;
            }
             
            case "teachmoveset" -> {
                handleTeachMoveset(event, user, userId);
                return;
            }

            case "releasepokemon" -> {
                handleReleasePokemon(event, user, userId);
                return;
            }
            case "cleardatabase" -> {
                handleClearDatabase(event, user, userId);
                return;
            }

            // TODO: Need a checkmoveset command
            // Will need to take team and pokemon arguments

            // TODO: Need a turnaction command for switch or move use
            // Will need to take battle and move argument. Needs to 
            // set the pending action for this user. 

            // TODO: Decide when actual battle logic will take place. 
            // Once both users have sent a pending action, is the turn done immediately
            // or only on another command entry? I'm thinking immediately upon receiving the second
            // action.
            
            default -> {
                event.reply("I can't handle that command right now :(")
                    .setEphemeral(true)
                    .queue(); 
            }
        }
    }

    private void sendMessage(User user, String message) {
        user.openPrivateChannel().queue((channel) -> {
            channel.sendMessage(message).queue();
        });
    }

    private void handleClearDatabase(SlashCommandInteractionEvent event, User user, long userId) {
        Member member = event.getMember();
            if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)) {
                event.reply("You do not have permission to clear the database!").setEphemeral(true).queue();
                return;
            }
        
        String eventName = event.getName();
        String confirmation = Optional.ofNullable(event.getOption("confirm"))
            .map(option -> option.getAsString())
            .orElse(null);
        LOGGER.info("Received slash command; '{}' with confirmation: '{}' from user: {} (ID: {})", eventName, confirmation, user, userId);
        if (confirmation != null && confirmation.equalsIgnoreCase("CONFIRM")) {

            

            // TODO(review 2026-04-20): Route destructive admin operations through a service boundary.
            // Bot layer should authorize and format replies; delete-all policy belongs in service/admin code so tests and safeguards live in one place.
            DatabaseSetup.deleteAllData();
            
            event.reply("Database cleared successfully!").queue();
        } else {
            event.reply("Database clear cancelled. To clear the database, you must type 'CONFIRM' in the confirmation option.").setEphemeral(true).queue();
        }
        return;
    }

    private void handleReleasePokemon(SlashCommandInteractionEvent event, User user, long userId) {
        // This will need to remove the specified Pokemon from the trainer's team in the database, then reply with success or failure message
        // We should probably have the user specify the team and slot number of the Pokemon they want to release, since there can be multiple of the same species and nickname across different teams and slots. 
        String eventName = event.getName();
        String releasedPokemon = Optional.ofNullable(event.getOption("pokemon"))
            .map(option -> option.getAsString())
            .orElse(null);
        LOGGER.info("Received slash command; '{}' with (nick)name: '{}' from user: {} (ID: {})", eventName, releasedPokemon, user, userId);
        
        Trainer releasingTrainer = trainerService.getTrainerByDiscordId(userId);
        if (releasingTrainer == null) {
            event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        
        Team releaseTeam = Optional.ofNullable(event.getOption("team"))
            .map(option -> teamService.getTeamFromName(releasingTrainer.getTrainerDbId(), option.getAsString()))
            .orElse(null);
        if (releaseTeam == null) {
            event.reply("You need to create a team first using /createteam!").setEphemeral(true).queue();
            return;
        }

        Pokemon pokemonToRelease = teamService.getPokemonByNickname(releasingTrainer.getTrainerDbId(), releaseTeam.getTeamDbId(), releasedPokemon);
        if (pokemonToRelease == null) {
            event.reply("No Pokémon with that nickname found on your team! Make sure you entered the correct nickname.").setEphemeral(true).queue();
            return;
        }
        // TODO(review 2026-04-21): Capture removed slot before releasePokemon(), or return that slot from TeamService.
        // currentTeamSlotIndex may change once Team removal stops piggybacking on dense List order.
        boolean releaseSuccess = teamService.releasePokemon(releaseTeam, pokemonToRelease);
        if (releaseSuccess) {
            event.reply("Successfully released " + pokemonToRelease.getNickname() + " from slot " + (pokemonToRelease.getCurrentTeamSlotIndex() + 1) + " of your team!").queue();
            return;
        } else {
            event.reply("Sorry, there was an error releasing that Pokémon from your team! Make sure you entered a valid slot index.").setEphemeral(true).queue();
            return;
        }
    }

    private void handleAddPokemon(SlashCommandInteractionEvent event, User user, long userId) {
        // Needs to create a pokemon and add it to the trainers team in the database, then reply with success or failure message
        String eventName = event.getName();
        String inputSpecies = Optional.ofNullable(event.getOption("species"))
            .map(option -> option.getAsString())
            .orElse(null);
        LOGGER.info("Received slash command; '{}' with Pokemon name: '{}' from user: {} (ID: {})", eventName, inputSpecies, user, userId);
        // below doesn't work because of the new enum structure with display names and aliases, need to loop through the enum values and check if the input matches either the display name or any of the aliases for each species 
        //PokeSpecies species = PokeSpecies.valueOf(event.getOption("species").getAsString().toUpperCase());
        
        PokeSpecies species = PokeSpecies.getSpeciesByString(inputSpecies);
        if (species == null) {
            event.reply("Sorry, that Pokemon hasn't been discovered yet!").setEphemeral(true).queue();
            return;
        }
        String nickname = Optional.ofNullable(event.getOption("nickname"))
            .map(option -> option.getAsString())
            .orElse(species.getDisplayName()); // If no nickname provided, use the species name as the nickname by default
        
        
        Trainer currentTrainer = trainerService.getTrainerByDiscordId(userId);
        if (currentTrainer == null) {
            event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        Team addTeam = teamService.getTeamFromName(currentTrainer.getTrainerDbId(), Optional.ofNullable(event.getOption("team"))
            .map(option -> option.getAsString())
            .orElse(null));
        if (addTeam == null) {
            event.reply("You need to create a team first using /createteam!").setEphemeral(true).queue();
            return;
        }

        if (addTeam.isFull()) {
            event.reply("Your team is full! Cannot add more Pokémon.").setEphemeral(true).queue();
            return;
        }

        Pokemon newPokemon = PokemonFactory.createPokemonFromRegistry(species, nickname);
        if (newPokemon == null) {
            event.reply("Sorry, that Pokemon hasn't been discovered yet!").setEphemeral(true).queue();
            return;
        } else {
            newPokemon.setTrainer(currentTrainer);
            // TODO(review 2026-04-20): Remove hardcoded level override or guard it behind explicit debug config.
            // Production addpokemon should not silently turn every new capture into level 50 test data.
            newPokemon.setLevel(50); // Set the Pokémon's level to 50 for testing purposes
            StatCalculator.calculateAllStats(newPokemon); // Recalculate stats based on level 50 for testing purposes
            newPokemon.healToFull();
            

            
            int slotIndex = teamService.addPokemonToTeam(addTeam, newPokemon);
            if (slotIndex == -1) {
                event.reply("Sorry, there was an error adding that Pokémon to your team!").setEphemeral(true).queue();
                return;
            } else if (slotIndex == -3) {
                event.reply("Your team is full! Cannot add more Pokémon.").setEphemeral(true).queue();
                return;
            } else {
                event.reply("Successfully added " + newPokemon.getNickname() + " to your team in slot " + (slotIndex + 1) + "!").queue();
                return;
            }
        }
    }

    private void handleCheckTeam(SlashCommandInteractionEvent event, User user, long userId) {
        String eventName = event.getName();
        LOGGER.info("Received slash command; '{}' from user: {} (ID: {})", eventName, user.getName(), userId  );

        String teamName = Optional.ofNullable(event.getOption("team"))
            .map(option -> option.getAsString())
            .orElse(null);
        if (teamName == null) {
            event.reply("Please specify a team name to check!").setEphemeral(true).queue();
            return;
        }
        
        Trainer trainer = trainerService.getTrainerByDiscordId(event.getUser().getIdLong());
        if (trainer == null) {
            event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        Team trainerTeam = teamService.getTeamFromName(trainer.getTrainerDbId(), teamName);
        if (trainerTeam == null) {
            event.reply("Team not found! Please specify a valid team name.").setEphemeral(true).queue();
            return;
        }

        List<Pokemon> teamInfo = trainerTeam.getTeamAsList();
        if (teamInfo == null || teamInfo.isEmpty()) {
            event.reply("Your team is currently empty!").queue();
            return;
        } else {
            StringBuilder teamMessage = new StringBuilder("Your current team:\n");
            // TODO(review 2026-04-21): If Team keeps empty middle slots, iterate real slot indexes instead of numbering filtered getTeamAsList() output 1..n.
            int slotNumber =  1;
            for (Pokemon p : teamInfo) {
                teamMessage.append("Slot ").append(slotNumber).append(":\n");
                teamMessage.append("- ").append(p.getNickname()).append("\n");
                teamMessage.append("  Species: ").append(p.getSpecies().getDisplayName()).append("\n");
                teamMessage.append("  Level: ").append(p.getLevel()).append("\n");
                LOGGER.info("Current HP for Pokemon in slot {} is {} for Pokemon with instance_id {} in trainer ID {}'s team.", slotNumber, p.getCurrentHP(), p.getPokemonDbId(), trainer.getTrainerDbId());
                teamMessage.append("  HP: ").append(p.getCurrentHP()).append("/").append(p.getMaxHP()).append("\n");
                teamMessage.append("  Attack: ").append(p.getCurrentAttack()).append("\n");
                teamMessage.append("  Defense: ").append(p.getCurrentDefense()).append("\n");
                teamMessage.append("  Special Attack: ").append(p.getCurrentSpecialAttack()).append("\n");
                teamMessage.append("  Special Defense: ").append(p.getCurrentSpecialDefense()).append("\n");
                teamMessage.append("  Speed: ").append(p.getCurrentSpeed()).append("\n\n");
                slotNumber++;
            }
            event.reply(teamMessage.toString()).queue();
        }
        return;
    }

    private void handleCreateTeam(SlashCommandInteractionEvent event, User user, long userId) {
        String teamName = Optional.ofNullable(event.getOption("teamname"))
            .map(option -> option.getAsString())
            .orElse(null);
        if (teamName == null) {
            event.reply("Please specify a team name to create!").setEphemeral(true).queue();
            return;
        }
        LOGGER.info("Received slash command; '{}' with content: '{}' from user: {} (ID: {})", event.getName(), teamName, user, userId);
        
        Trainer teamCreator = trainerService.getTrainerByDiscordId(userId);
        if (teamCreator == null) {
            event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        Team teamCreateAttempt = teamService.createTeam(teamCreator.getTrainerDbId(), teamName);
        if (teamCreateAttempt == null) {
            event.reply("Sorry, there was an error creating your team!").setEphemeral(true).queue();
            return;
        } else {
            event.reply("Team created successfully! You can have up to 3 teams. Use /checkteam to see your current teams and /addpokemon to add Pokemon to them!").queue();
            return;
        }
    }

    private void handleCreateTrainer(SlashCommandInteractionEvent event, User user, long userId) {
        String trainerName = Optional.ofNullable(event.getOption("name"))
            .map(option -> option.getAsString())
            .orElse(null);
        if (trainerName == null) {
            event.reply("Please specify a trainer name to create!").setEphemeral(true).queue();
            return;
        }
        LOGGER.info("Received slash command; '{}' with content: '{}' from user: {} (ID: {})", event.getName(), trainerName, user, userId);

        Trainer createAttempt = trainerService.createTrainer(trainerName, userId, user.getName());

        if (createAttempt == null) {
            event.reply("A trainer already exists for your Discord account!").setEphemeral(true).queue();
            return;
        } else {
            event.reply("Trainer created successfully! You can now create teams with /createteam and add Pokémon to them with /addpokemon!").queue();
            return;
        }
    }

    private void handleStartBattle(SlashCommandInteractionEvent event, User user, long userId) {
        String opponentName = Optional.ofNullable(event.getOption("opponent"))
            .map(option -> option.getAsUser().getName())
            .orElse(null);
        LOGGER.info("Received slash command; '{}' with content: '{}' from user: {} (ID: {})", event.getName(), opponentName, user, userId  );
        // We need to check that both users have trainers, create a battle record in the database,
        // and then reply with the battle ID or some kind of confirmation message. 
        // The battle loop will be handled separately, and the battle state can be checked with the /battlestate command.
        // If the attacking trainer doesn't have a team set up yet, we should reply with a 
        // message telling them to set up their team first using /checkteam and /addpokemon.

        Trainer attackingTrainer = trainerService.getTrainerByDiscordId(userId);
        if (attackingTrainer == null) {
            event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        String challengerTeamName = Optional.ofNullable(event.getOption("team"))
            .map(option -> option.getAsString())
            .orElse(null);
        if (challengerTeamName == null) {
            event.reply("Please specify a team name to use for the battle!").setEphemeral(true).queue();
            return;
        }

        Team challengerTeam = teamService.getTeamFromName(attackingTrainer.getTrainerDbId(), challengerTeamName);
        if (challengerTeam == null) {
            event.reply("You need to set up your team first using /checkteam and /addpokemon!").setEphemeral(true).queue();
            return;
        }

        Trainer defendingTrainer = Optional.ofNullable(event.getOption("opponent"))
            .map(option -> trainerService.getTrainerByDiscordId(option.getAsUser().getIdLong()))
            .orElse(null);
        if (defendingTrainer == null) {
            event.reply("Your opponent needs to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        if (defendingTrainer.getTrainerDbId() == attackingTrainer.getTrainerDbId()) {
            event.reply("You can't challenge yourself to a battle!").setEphemeral(true).queue();
            return;
         }
        // We won't check the defending trainer's team here, since they might want to set it up after the battle is initiated.

        if (battleService.createBattle(attackingTrainer.getTrainerDbId(), defendingTrainer.getTrainerDbId(), Optional.ofNullable(challengerTeam.getTeamDbId()), Optional.empty())) {
            event.reply("Challenge issued successfully!").queue();

            // Notify the opponent trainer of the challenge (e.g., via Discord DM or in-app notification)
            Optional.ofNullable(event.getOption("opponent"))
                .map(option -> option.getAsUser())
                .ifPresent(opponentUser -> sendMessage(opponentUser, "You have been challenged to a battle by " + attackingTrainer.getTrainerName() + "(" + user.getName() + ")! Use /battlestate to check the status of the battle and /checkteam to set up your team if you haven't already. You can accept with /acceptchallenge or decline with /declinechallenge (not implemented yet)."));
        } else {
            event.reply("There is already an active or pending battle between you and your opponent!").setEphemeral(true).queue();
        }

        return;
    }

    private void handleTeachMoveset(SlashCommandInteractionEvent event, User user, long userId) {

        // Checking for a full moveset and allowing for swapping of moves is a good goal, but we will not do that
        // just yet. Gotta get a move teaching command working first.
        String eventName = event.getName();
        LOGGER.info("Received slash command; '{}' from user: {} (ID: {})", eventName, user.getName(), userId  );

        String teamName = Optional.ofNullable(event.getOption("team"))
            .map(option -> option.getAsString())
            .orElse(null);
        String pokemonName = Optional.ofNullable(event.getOption("pokemon"))
            .map(option -> option.getAsString())
            .orElse(null);
        if (teamName == null || pokemonName == null) {
            event.reply("Please specify a team name and the nickname of the Pokémon you want to teach the move to!").setEphemeral(true).queue();
            return;
        }
        
        Trainer trainer = trainerService.getTrainerByDiscordId(event.getUser().getIdLong());
        if (trainer == null) {
            event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        Team trainerTeam = teamService.getTeamFromName(trainer.getTrainerDbId(), teamName);
        if (trainerTeam == null) {
            event.reply("Team not found! Please specify a valid team name.").setEphemeral(true).queue();
            return;
        }

        Pokemon selectedPokemon = teamService.getPokemonByNickname(trainer.getTrainerDbId(), trainerTeam.getTeamDbId(), pokemonName);
        if (selectedPokemon == null) {
            event.reply("No Pokémon with that nickname found on your team! Make sure you entered the correct nickname.").setEphemeral(true).queue();
            return;
        }

        // If we made it here, we have a valid trainer, team, and pokemon, so we can proceed with teaching the move.
        // The user will input the move names as options in the slash command, and we will validate that the moves are in the Pokémon's learnset and then update the Pokémon's moveset in the database accordingly.
        // We should also check that the user isn't trying to teach more than 4 moves, since that's the maximum moveset size in Gen 1.

        
        List<String> moveNames = Stream.of(
            "moveone", "movetwo", "movethree", "movefour")
            .map(name -> event.getOption(name))
            .filter(Objects::nonNull)
            .map(option -> option.getAsString())
            .distinct()
            .toList();
        
            
        for (String moveName : moveNames) {
            boolean foundInLearnset = false;
            Move moveToTeach = moveSlotService.getMoveByName(moveName);
            if (moveToTeach == null) {
                event.reply("Move '" + moveName + "' not found! Please make sure you entered the move name correctly.").setEphemeral(true).queue();
                return;
            }
            for (LearnsetEntry entry : moveSlotService.getEligibleMoves(selectedPokemon)) {
                if (entry.getMove().getMoveName().equalsIgnoreCase(moveName)) {
                    if (entry.getSource() == pokemonGame.model.LearnsetEntry.Source.LEVEL && entry.getParameter() > selectedPokemon.getLevel()) {
                        event.reply("Your Pokémon doesn't meet the level requirement to learn " + moveName + "!").setEphemeral(true).queue();
                        return;
                    }
                    foundInLearnset = true;
                    break;
                }
            }
            if (!foundInLearnset) {
                event.reply("Move '" + moveName + "' is not in " + selectedPokemon.getNickname() + "'s learnset!").setEphemeral(true).queue();
                return;
            }

            moveSlotService.teachMove(selectedPokemon, moveToTeach);
        }
        event.reply("Successfully taught " + moveNames.size() + " moves to " + selectedPokemon.getNickname() + "!").queue();
    }
        
        
    }

