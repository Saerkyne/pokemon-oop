package pokemonGame.bot;
import pokemonGame.App;
import pokemonGame.Pokemon;
import pokemonGame.PokemonFactory;
import pokemonGame.Trainer;
import pokemonGame.db.DatabaseSetup;
import pokemonGame.db.PokemonCRUD;
import pokemonGame.db.TeamCRUD;
import pokemonGame.db.TrainerCRUD;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlashExample extends ListenerAdapter{

    private static final Logger LOGGER = LoggerFactory.getLogger(SlashExample.class);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) {
            return;
        }
        String user = event.getUser().getName();
        long userId = event.getUser().getIdLong();
        TrainerCRUD trainerCRUD = new TrainerCRUD();
        PokemonCRUD pokemonCRUD = new PokemonCRUD();
        TeamCRUD teamCRUD = new TeamCRUD();



        switch (event.getName()) {
            case "say":
                LOGGER.info("Received slash command: '" + event.getName() + "' with content: '" + event.getOption("content").getAsString() + "' from user: " + user + " (ID: " + userId + ")");
                say(event, event.getOption("content").getAsString());
                break;

            case "ping":
                LOGGER.info("Received slash command: '" + event.getName() + "' from user: " + user + " (ID: " + userId + ")");
                event.reply("Pong!").queue();
                break;
            
            case "battlestate":
                LOGGER.info("Received slash command: '" + event.getName() + "' from user: " + user + " (ID: " + userId + ")");
                event.reply("The battle is currently in progress!").queue();
                break;

            case "createtrainer":
                LOGGER.info("Received slash command: '" + event.getName() + "' from user: " + user + " (ID: " + userId + ")");
                int createAttempt = App.createTrainer(event.getOption("name").getAsString(), userId, user);

                if (createAttempt == -1) {
                    event.reply("You are already a trainer!").setEphemeral(true).queue();
                    break;
                } else {
                    event.reply("Trainer created successfully!").queue();
                    break;
                }
            
            case "checkteam":
                LOGGER.info("Received slash command: '" + event.getName() + "' from user: " + user + " (ID: " + userId + ")");
                
                
                Trainer trainer = trainerCRUD.getTrainerByDiscordId(userId);

                List<Pokemon> teamInfo = teamCRUD.getDBTeamForTrainer(trainer);

                if (teamInfo.isEmpty()) {
                    event.reply("Your team is currently empty!").queue();
                } else {
                    StringBuilder teamMessage = new StringBuilder("Your current team:\n");
                    int slotNumber = teamInfo.size() - teamInfo.size() + 1; // Calculate the slot number based on the size of the team
                    for (Pokemon p : teamInfo) {
                        teamMessage.append("Slot ").append(slotNumber).append(":\n");
                        teamMessage.append("- ").append(p.getNickname()).append("\n");
                        teamMessage.append("  Species: ").append(p.getSpecies()).append("\n");
                        teamMessage.append("  Level: ").append(p.getLevel()).append("\n");
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
                break;

            case "addpokemon":
                // Needs to create a pokemon and add it to the trainers team in the database, then reply with success or failure message
                LOGGER.info("Received slash command: '" + event.getName() + "' with Pokemon name: '" + event.getOption("pokemon").getAsString() + "' from user: " + user + " (ID: " + userId + ")");
                String species = event.getOption("pokemon").getAsString();
                String nickname = event.getOption("nickname") != null ? event.getOption("nickname").getAsString() : null;
                
                
                Trainer currentTrainer = trainerCRUD.getTrainerByDiscordId(userId);
                if (currentTrainer == null) {
                    event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
                    return;
                } else {

                    if (teamCRUD.checkSlotIndex(currentTrainer.getDbId()) >= 6) {
                        if (teamCRUD.checkSlotIndex(currentTrainer.getDbId()) >= 6) {
                        event.reply("Your team is full! Cannot add more Pokémon.").setEphemeral(true).queue();
                        return;
                        }
                    }

                    Pokemon newPokemon = PokemonFactory.createPokemonFromRegistry(species, nickname);
                    if (newPokemon == null) {
                        event.reply("Sorry, that Pokemon hasn't been discovered yet!").setEphemeral(true).queue();
                        return;
                    } else {
                        newPokemon.setTrainer(currentTrainer);
                        newPokemon.setLevel(50); // Set the Pokémon's level to 50 for testing purposes
                        newPokemon.calculateCurrentStats(); // Recalculate stats based on IVs, EVs, and level

                        int pokemonId = pokemonCRUD.createDBPokemon(newPokemon);
                        if (pokemonId == -1) {
                            event.reply("Sorry, there was an error adding that Pokémon to your team!").setEphemeral(true).queue();
                            return;
                        } else {
                            int slotIndex = teamCRUD.addPokemonToDBTeam(currentTrainer.getDbId(), pokemonId);
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
                }

            case "releasepokemon":
                LOGGER.info("Received slash command: '" + event.getName() + "' with (nick)name: '" + event.getOption("pokemon").getAsString() + "' from user: " + user + " (ID: " + userId + ")");
                String releasedPokemon = event.getOption("pokemon").getAsString();
                Trainer releasingTrainer = trainerCRUD.getTrainerByDiscordId(userId);
                

                if (releasingTrainer == null) {
                    event.reply("You need to create a trainer first using /createtrainer!").setEphemeral(true).queue();
                    return;
                } else {

                    Pokemon pokemonToRelease = PokemonCRUD.getPokemonByNicknameAndTrainer(releasedPokemon, releasingTrainer);
                    int slotToRelease = teamCRUD.getSlotIndexForPokemon(releasingTrainer.getDbId(), pokemonToRelease.getId());
                    boolean releaseSuccess = teamCRUD.removePokemonFromDBTeam(releasingTrainer.getDbId(), slotToRelease);
                    if (releaseSuccess) {
                        event.reply("Successfully released " + pokemonToRelease.getNickname() + " from slot " + (slotToRelease + 1) + " of your team!").queue();
                        teamCRUD.reorderTeamAfterRelease(releasingTrainer.getDbId());
                        return;
                    } else {
                        event.reply("Sorry, there was an error releasing that Pokémon from your team! Make sure you entered a valid slot index.").setEphemeral(true).queue();
                        return;
                    }
                }
                
            case "cleardatabase":
                LOGGER.info("Received slash command: '" + event.getName() + "' with confirmation: '" + event.getOption("confirm").getAsString() + "' from user: " + user + " (ID: " + userId + ")");
                String confirmation = event.getOption("confirm").getAsString();
                if (confirmation.equalsIgnoreCase("CONFIRM")) {
                    if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                        event.reply("You do not have permission to clear the database!").setEphemeral(true).queue();
                        return;
                    }
                    

                    DatabaseSetup.deleteAllData();
                    
                    event.reply("Database cleared successfully!").queue();
                } else {
                    event.reply("Database clear cancelled. To clear the database, you must type 'CONFIRM' in the confirmation option.").setEphemeral(true).queue();
                }
                break;
            
            default:
                event.reply("I can't handle that command right now :(")
                    .setEphemeral(true)
                    .queue();  
        }
    }


    public void say(SlashCommandInteractionEvent event, String content) {
        event.reply(content).queue();
    }
}
