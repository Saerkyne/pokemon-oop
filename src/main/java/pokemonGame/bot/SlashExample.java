package pokemonGame.bot;
import pokemonGame.db.DatabaseSetup;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.model.Trainer;
import pokemonGame.service.BattleService;
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
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlashExample extends ListenerAdapter{

    private static final Logger LOGGER = LoggerFactory.getLogger(SlashExample.class);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        long userId = user.getIdLong();
        String eventName = event.getName();
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
                

            case "releasepokemon" -> {
                handleReleasePokemon(event, user, userId);
                return;
            }
            case "cleardatabase" -> {
                handleClearDatabase(event, user, userId);
                return;
            }
            
            default -> {
                event.reply("I can't handle that command right now :(")
                    .setEphemeral(true)
                    .queue(); 
            }
        }
    }

    public void sendMessage(User user, String message) {
        user.openPrivateChannel().queue((channel) -> {
            channel.sendMessage(message).queue();
        });
    }

    private void handleClearDatabase(SlashCommandInteractionEvent event, User user, long userId) {
        String eventName = event.getName();
        String confirmation = event.getOption("confirm") != null ? event.getOption("confirm").getAsString() : "";
        LOGGER.info("Received slash command; '{}' with confirmation: '{}' from user: {} (ID: {})", eventName, confirmation, user, userId);
        if (confirmation.equalsIgnoreCase("CONFIRM")) {

            Member member = event.getMember();
            if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)) {
                event.reply("You do not have permission to clear the database!").setEphemeral(true).queue();
                return;
            }

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
        String releasedPokemon = event.getOption("pokemon").getAsString();
        LOGGER.info("Received slash command; '{}' with (nick)name: '{}' from user: {} (ID: {})", eventName, releasedPokemon, user, userId);
        TrainerService trainerService = new TrainerService();
        TeamService teamService = new TeamService();
        
        Trainer releasingTrainer = trainerService.getTrainerByDiscordId(userId);
        if (releasingTrainer == null) {
            event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        
        Team releaseTeam = teamService.getTeamFromName(releasingTrainer.getTrainerDbId(), event.getOption("team").getAsString());
        if (releaseTeam == null) {
            event.reply("You need to create a team first using /createteam!").setEphemeral(true).queue();
            return;
        }

        Pokemon pokemonToRelease = teamService.getPokemonByNickname(releasingTrainer.getTrainerDbId(), releaseTeam.getTeamDbId(), releasedPokemon);
        if (pokemonToRelease == null) {
            event.reply("No Pokémon with that nickname found on your team! Make sure you entered the correct nickname.").setEphemeral(true).queue();
            return;
        }
        
        
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
        String inputSpecies = event.getOption("species").getAsString();
        LOGGER.info("Received slash command; '{}' with Pokemon name: '{}' from user: {} (ID: {})", eventName, inputSpecies, user, userId);
        // below doesn't work because of the new enum structure with display names and aliases, need to loop through the enum values and check if the input matches either the display name or any of the aliases for each species 
        //PokeSpecies species = PokeSpecies.valueOf(event.getOption("species").getAsString().toUpperCase());
        TeamService teamService = new TeamService();
        TrainerService trainerService = new TrainerService();

        PokeSpecies species = PokeSpecies.getSpeciesByString(inputSpecies);
        if (species == null) {
            event.reply("Sorry, that Pokemon hasn't been discovered yet!").setEphemeral(true).queue();
            return;
        }
        String nickname = event.getOption("nickname") != null ? event.getOption("nickname").getAsString() : species.getDisplayName();
        
        
        Trainer currentTrainer = trainerService.getTrainerByDiscordId(userId);
        if (currentTrainer == null) {
            event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        Team addTeam = teamService.getTeamFromName(currentTrainer.getTrainerDbId(), event.getOption("team").getAsString());
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
            newPokemon.setLevel(50); // Set the Pokémon's level to 50 for testing purposes
            StatCalculator.calculateAllStats(newPokemon);; // Recalculate stats based on level 50 for testing purposes
            

            
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

        String teamName = event.getOption("team").getAsString();
        if (teamName == null) {
            event.reply("Please specify a team name to check!").setEphemeral(true).queue();
            return;
        }
        TrainerService trainerService = new TrainerService();
        TeamService teamService = new TeamService();
        
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
        String teamName = event.getOption("teamname").getAsString();
        if (teamName == null) {
            event.reply("Please specify a team name to create!").setEphemeral(true).queue();
            return;
        }
        LOGGER.info("Received slash command; '{}' with content: '{}' from user: {} (ID: {})", event.getName(), teamName, user, userId);
        TrainerService trainerService = new TrainerService();
        TeamService teamService = new TeamService();
        
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
        String trainerName = event.getOption("name").getAsString();
        if (trainerName == null) {
            event.reply("Please specify a trainer name to create!").setEphemeral(true).queue();
            return;
        }
        LOGGER.info("Received slash command; '{}' with content: '{}' from user: {} (ID: {})", event.getName(), trainerName, user, userId);
        TrainerService trainerService = new TrainerService();

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
        String opponentName = event.getOption("opponent").getAsUser().getName();
        LOGGER.info("Received slash command; '{}' with content: '{}' from user: {} (ID: {})", event.getName(), opponentName, user, userId  );
        // We need to check that both users have trainers, create a battle record in the database,
        // and then reply with the battle ID or some kind of confirmation message. 
        // The battle loop will be handled separately, and the battle state can be checked with the /battlestate command.
        // If the attacking trainer doesn't have a team set up yet, we should reply with a 
        // message telling them to set up their team first using /checkteam and /addpokemon.
        TrainerService trainerService = new TrainerService();
        TeamService teamService = new TeamService();

        Trainer attackingTrainer = trainerService.getTrainerByDiscordId(userId);
        if (attackingTrainer == null) {
            event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        String challengerTeamName = event.getOption("team").getAsString();
        if (challengerTeamName == null) {
            event.reply("Please specify a team name to use for the battle!").setEphemeral(true).queue();
            return;
        }

        Team challengerTeam = teamService.getTeamFromName(attackingTrainer.getTrainerDbId(), challengerTeamName);
        if (challengerTeam == null) {
            event.reply("You need to set up your team first using /checkteam and /addpokemon!").setEphemeral(true).queue();
            return;
        }

        Trainer defendingTrainer = trainerService.getTrainerByDiscordId(event.getOption("opponent").getAsUser().getIdLong());
        if (defendingTrainer == null) {
            event.reply("Your opponent needs to create a trainer first using /createtrainer!").setEphemeral(true).queue();
            return;
        }
        // We won't check the defending trainer's team here, since they might want to set it up after the battle is initiated.

        if (BattleService.createChallenge(attackingTrainer.getTrainerDbId(), defendingTrainer.getTrainerDbId())) {
            event.reply("Challenge issued successfully!").queue();

            // Notify the opponent trainer of the challenge (e.g., via Discord DM or in-app notification)
            sendMessage(event.getOption("opponent").getAsUser(), "You have been challenged to a battle by " + attackingTrainer.getTrainerName() + "(" + user.getName() + ")! Use /battlestate to check the status of the battle and /checkteam to set up your team if you haven't already. You can accept with /acceptchallenge or decline with /declinechallenge (not implemented yet).");
        } else {
            event.reply("There is already an active or pending battle between you and your opponent!").setEphemeral(true).queue();
        }

        return;
    }
}
