package pokemonGame.bot;
import pokemonGame.db.DatabaseSetup;
import pokemonGame.db.PokemonCRUD;
import pokemonGame.db.TeamCRUD;
import pokemonGame.db.TrainerCRUD;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.model.Trainer;
import pokemonGame.service.BattleService;
import pokemonGame.service.TrainerService;
import pokemonGame.species.PokeSpecies;
import pokemonGame.species.PokemonFactory;
import pokemonGame.core.StatCalculator;
//   import pokemonGame.TeamService;
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
        String user = event.getUser().getName();
        long userId = event.getUser().getIdLong();
        TrainerCRUD trainerCRUD = new TrainerCRUD();
        PokemonCRUD pokemonCRUD = new PokemonCRUD();
        TeamCRUD teamCRUD = new TeamCRUD();
        // TODO: Replace the three CRUD instantiations above with:
        //   TrainerService trainerService = new TrainerService();
        //   TeamService teamService = new TeamService();
        // Then update each case below to call service methods instead of CRUD methods directly.
        // For example:
        //   trainerCRUD.getTrainerByDiscordId(userId)  →  trainerService.getTrainerByDiscordId(userId)
        //   teamCRUD.createTeamForTrainer(...)          →  teamService.createTeam(...)
        //   teamCRUD.getDBTeamForTrainer(...)            →  teamService.loadTeam(...)
        //   teamCRUD.addPokemonToDBTeam(...)             →  teamService.addPokemonToTeam(team, pokemon)
        //   teamCRUD.getSlotIndexForPokemon(...) + removePokemonFromDBTeam(...) + reorderTeamAfterRelease(...)
        //       →  teamService.releasePokemon(team, pokemon)   (one call replaces three)
        //   Trainer.createTrainer(...)                  →  trainerService.createTrainer(...)

        /*
        NOTICED ISSUE - USERS CAN HAVE MULTIPLE BATTLES WITH DIFFERENT PEOPLE AT THE SAME TIME
        THERE IS NO LOGIC FOR KEEPING DIFFERENT TEAMS SEPARATE - IN FACT, EVERY USER CAN ONLY HAVE
        ONE TEAM CURRENTLY. THIS MUST BE CHANGED TO ALLOW FOR MULTIPLE TEAMS PER USER,
        AND THE TEAMS MUST BE LINKED TO SPECIFIC BATTLES, SO THAT USERS DON'T HAVE THE SAME POKEMON
        INSTANCE FIGHTING IN TWO DIFFERENT BATTLES AT THE SAME TIME, WHICH CAUSES ALL KINDS OF ISSUES WITH HP TRACKING, ETC.
        */



        switch (event.getName()) {
            
            case "battlestate":
                LOGGER.info("Received slash command; '{}' from user: {} (ID: {})", event.getName(), user, userId);
                event.reply("The battle is currently in progress!").queue();
                return;

            case "startbattle":
                LOGGER.info("Received slash command; '{}' with content: '{}' from user: {} (ID: {})", event.getName(), event.getOption("opponent").getAsUser().getName(), user, userId);
                // We need to check that both users have trainers, create a battle record in the database,
                // and then reply with the battle ID or some kind of confirmation message. 
                // The battle loop will be handled separately, and the battle state can be checked with the /battlestate command.
                // If the attacking trainer doesn't have a team set up yet, we should reply with a 
                // message telling them to set up their team first using /checkteam and /addpokemon.
                Trainer attackingTrainer = trainerCRUD.getTrainerByDiscordId(userId);
                if (attackingTrainer == null) {
                    event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
                    return;
                }
                String challengerTeamName = event.getOption("team").getAsString();
                Team challengerTeam = teamCRUD.getTeamByNameForTrainer(attackingTrainer.getTrainerDbId(), challengerTeamName);
                if (challengerTeam == null) {
                    event.reply("You need to set up your team first using /checkteam and /addpokemon!").setEphemeral(true).queue();
                    return;
                }

                Trainer defendingTrainer = trainerCRUD.getTrainerByDiscordId(event.getOption("opponent").getAsUser().getIdLong());
                if (defendingTrainer == null) {
                    event.reply("Your opponent needs to create a trainer first using /createtrainer!").setEphemeral(true).queue();
                    return;
                }
                // We won't check the defending trainer's team here, since they might want to set it up after the battle is initiated.

                if (BattleService.createChallenge(attackingTrainer.getTrainerDbId(), defendingTrainer.getTrainerDbId())) {
                    event.reply("Challenge issued successfully!").queue();

                    // Notify the opponent trainer of the challenge (e.g., via Discord DM or in-app notification)
                    sendMessage(event.getOption("opponent").getAsUser(), "You have been challenged to a battle by " + attackingTrainer.getTrainerName() + "(" + user + ")! Use /battlestate to check the status of the battle and /checkteam to set up your team if you haven't already. You can accept with /acceptchallenge or decline with /declinechallenge (not implemented yet).");
                } else {
                    event.reply("There is already an active or pending battle between you and your opponent!").setEphemeral(true).queue();
                }

                return;
            
            case "createtrainer":
                LOGGER.info("Received slash command; '{}' with content: '{}' from user: {} (ID: {})", event.getName(), event.getOption("name").getAsString(), user, userId);
                TrainerService trainerService = new TrainerService();
                Trainer createAttempt = trainerService.createTrainer(event.getOption("name").getAsString(), userId, user);

                if (createAttempt == null) {
                    event.reply("A trainer already exists for your Discord account!").setEphemeral(true).queue();
                    return;
                } else {
                    event.reply("Trainer created successfully! You can now create teams with /createteam and add Pokémon to them with /addpokemon!").queue();
                    return;
                }
            
            case "createteam":
                LOGGER.info("Received slash command; '{}' with content: '{}' from user: {} (ID: {})", event.getName(), event.getOption("teamname").getAsString(), user, userId);
                Trainer teamCreator = trainerCRUD.getTrainerByDiscordId(userId);
                if (teamCreator == null) {
                    event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
                    return;
                }
                int teamCreateAttempt = teamCRUD.createTeamForTrainer(teamCreator.getTrainerDbId(), event.getOption("teamname").getAsString());
                if (teamCreateAttempt == -1) {
                    event.reply("Sorry, there was an error creating your team!").setEphemeral(true).queue();
                    return;
                } else {
                    event.reply("Team created successfully! You can have up to 3 teams. Use /checkteam to see your current teams and /addpokemon to add Pokemon to them!").queue();
                    return;
                }
            
            case "checkteam":
                LOGGER.info("Received slash command; '{}' from user: {} (ID: {})", event.getName(), user, userId);

                String teamName = event.getOption("team").getAsString();
                
                Trainer trainer = trainerCRUD.getTrainerByDiscordId(userId);
                if (trainer == null) {
                    event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
                    return;
                }
                Team trainerTeam = teamCRUD.getTeamByNameForTrainer(trainer.getTrainerDbId(), teamName);

                List<Pokemon> teamInfo = teamCRUD.getDBTeamForTrainer(trainer.getTrainerDbId(), trainerTeam.getTeamDbId()).getPokemonList();
                

                if (!(trainerTeam.getTeamSize() > 0)) {
                    event.reply("Your team is currently empty!").queue();
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

            case "addpokemon":
                // Needs to create a pokemon and add it to the trainers team in the database, then reply with success or failure message
                LOGGER.info("Received slash command; '{}' with Pokemon name: '{}' from user: {} (ID: {})", event.getName(), event.getOption("species").getAsString(), user, userId);
                // below doesn't work because of the new enum structure with display names and aliases, need to loop through the enum values and check if the input matches either the display name or any of the aliases for each species 
                //PokeSpecies species = PokeSpecies.valueOf(event.getOption("species").getAsString().toUpperCase());

                String inputSpecies = event.getOption("species").getAsString();
                PokeSpecies species = PokeSpecies.getSpeciesByString(inputSpecies);
                if (species == null) {
                    event.reply("Sorry, that Pokemon hasn't been discovered yet!").setEphemeral(true).queue();
                    return;
                }
                String nickname = event.getOption("nickname") != null ? event.getOption("nickname").getAsString() : species.getDisplayName();
                
                
                Trainer currentTrainer = trainerCRUD.getTrainerByDiscordId(userId);
                if (currentTrainer == null) {
                    event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
                    return;
                }
                Team addTeam = teamCRUD.getTeamByNameForTrainer(currentTrainer.getTrainerDbId(), event.getOption("team").getAsString());
                if (addTeam == null) {
                    event.reply("You need to create a team first using /createteam!").setEphemeral(true).queue();
                    return;
                }
                
                

                if (teamCRUD.checkSlotIndex(currentTrainer.getTrainerDbId(), addTeam.getTeamDbId()) >= 6) {
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
                    

                    int pokemonId = pokemonCRUD.createDBPokemon(newPokemon);
                    if (pokemonId == -1) {
                        event.reply("Sorry, there was an error adding that Pokémon to your team!").setEphemeral(true).queue();
                        return;
                    } else {
                        int slotIndex = teamCRUD.addPokemonToDBTeam(currentTrainer.getTrainerDbId(), addTeam.getTeamDbId(), pokemonId);
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

            case "releasepokemon":
                LOGGER.info("Received slash command; '{}' with (nick)name: '{}' from user: {} (ID: {})", event.getName(), event.getOption("pokemon").getAsString(), user, userId);
                String releasedPokemon = event.getOption("pokemon").getAsString();
                
                Trainer releasingTrainer = trainerCRUD.getTrainerByDiscordId(userId);
                if (releasingTrainer == null) {
                    event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
                    return;
                }
                

                Pokemon pokemonToRelease = PokemonCRUD.getPokemonByNicknameAndTrainer(releasedPokemon, releasingTrainer);
                if (pokemonToRelease == null) {
                    event.reply("No Pokémon with that nickname found on your team! Make sure you entered the correct nickname.").setEphemeral(true).queue();
                    return;
                }
                Team releaseTeam = teamCRUD.getTeamByNameForTrainer(releasingTrainer.getTrainerDbId(), event.getOption("team").getAsString());
                if (releaseTeam == null) {
                    event.reply("You need to create a team first using /createteam!").setEphemeral(true).queue();
                    return;
                }
                int slotToRelease = teamCRUD.getSlotIndexForPokemon(releasingTrainer.getTrainerDbId(), releaseTeam.getTeamDbId(), pokemonToRelease.getPokemonDbId());
                boolean releaseSuccess = teamCRUD.removePokemonFromDBTeam(releasingTrainer.getTrainerDbId(), releaseTeam.getTeamDbId(), slotToRelease);
                if (releaseSuccess) {
                    event.reply("Successfully released " + pokemonToRelease.getNickname() + " from slot " + (slotToRelease + 1) + " of your team!").queue();
                    teamCRUD.reorderTeamAfterRelease(releasingTrainer.getTrainerDbId(), releaseTeam.getTeamDbId());
                    return;
                } else {
                    event.reply("Sorry, there was an error releasing that Pokémon from your team! Make sure you entered a valid slot index.").setEphemeral(true).queue();
                    return;
                }
                
            case "cleardatabase":
                LOGGER.info("Received slash command; '{}' with confirmation: '{}' from user: {} (ID: {})", event.getName(), event.getOption("confirm").getAsString(), user, userId);
                String confirmation = event.getOption("confirm").getAsString();
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
            
            default:
                event.reply("I can't handle that command right now :(")
                    .setEphemeral(true)
                    .queue();  
        }
    }

    public void sendMessage(User user, String message) {
        user.openPrivateChannel().queue((channel) -> {
            channel.sendMessage(message).queue();
        });
    }

}
