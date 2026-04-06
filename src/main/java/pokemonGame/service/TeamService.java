package pokemonGame.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.db.PokemonCRUD;
import pokemonGame.db.TeamCRUD;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;

/**
 * Service layer for team-related operations. Sits between the bot/controller
 * layer and the persistence layer ({@link TeamCRUD}, {@link PokemonCRUD}),
 * coordinating multi-step workflows that involve both domain objects and
 * database calls.
 *
 * <p><b>Why this class exists (separation of concerns):</b> Without a service
 * layer, the bot event handlers in {@code SlashExample} call DAO methods
 * directly, and domain objects like {@link Team} also call DAOs from inside
 * their own methods. This tangles three responsibilities together:</p>
 * <ol>
 *   <li>Parsing Discord input (bot layer's job)</li>
 *   <li>Business logic and validation (service layer's job)</li>
 *   <li>SQL execution (DAO layer's job)</li>
 * </ol>
 *
 * <p>By moving the business logic here, {@link Team} becomes a pure domain
 * object (just data + in-memory behavior like {@code isFull()},
 * {@code contains()}, {@code getTeamSize()}), and {@code SlashExample}
 * becomes a thin controller that parses input, calls a service method, and
 * formats the reply.</p>
 *
 * <p><b>The layered call flow becomes:</b></p>
 * <pre>
 *   SlashExample (parse Discord input)
 *       → TeamService (validate, coordinate, log)
 *           → TeamCRUD / PokemonCRUD (execute SQL)
 *           → Team / Pokemon (update in-memory state)
 *       ← returns result
 *   SlashExample (format Discord reply)
 * </pre>
 *
 * @see Team
 * @see TeamCRUD
 * @see TrainerService
 */
public class TeamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);

    private final TeamCRUD teamCRUD;
    private final PokemonCRUD pokemonCRUD;

    public TeamService() {
        this.teamCRUD = new TeamCRUD();
        this.pokemonCRUD = new PokemonCRUD();
    }

    /**
     * Creates a new named team for a trainer and persists it to the database.
     * Returns a {@link Team} domain object with its database ID set, or
     * {@code null} if creation failed.
     *
     * <p>This replaces the direct {@code teamCRUD.createTeamForTrainer()} call
     * that was previously in {@code SlashExample} and {@code Team.createDbTeamForTrainer()}.</p>
     *
     * @param trainerId the trainer's database ID
     * @param teamName  the display name for the new team
     * @return a populated {@link Team}, or {@code null} on failure
     */
    public Team createTeam(int trainerId, String teamName) {
        int teamId = teamCRUD.createTeamForTrainer(trainerId, teamName);
        if (teamId == -1) {
            LOGGER.error("Failed to create team '{}' for trainer ID {}.", teamName, trainerId);
            return null;
        }

        Team team = new Team(teamName);
        team.setTeamDbId(teamId);
        team.setTrainerDbId(trainerId);
        LOGGER.info("Created team '{}' (ID {}) for trainer ID {}.", teamName, teamId, trainerId);
        return team;
    }

    /**
     * Loads a team and its Pokémon from the database, returning a fully
     * populated {@link Team} domain object.
     *
     * <p>This replaces the direct {@code teamCRUD.getDBTeamForTrainer()} calls
     * that were previously in {@code SlashExample} and {@code Team.loadDbTeamForTrainer()}.</p>
     *
     * @param trainerId the trainer's database ID
     * @param teamId    the team's database ID
     * @return a {@link Team} with its {@code pokemonList} populated
     */
    public Team loadTeam(int trainerId, int teamId) {
        Team team = teamCRUD.getDBTeamForTrainer(trainerId, teamId);
        if (team != null) {
            team.setTrainerDbId(trainerId);
            team.setTeamDbId(teamId);
            // Also load the team name from the DB if it wasn't set by getDBTeamForTrainer
            if (team.getTeamName() == null || team.getTeamName().equals("Loaded Team")) {
                String name = teamCRUD.getTeamName(trainerId, teamId);
                if (name != null) {
                    team.setTeamName(name);
                }
            }
            LOGGER.info("Loaded team '{}' (ID {}) with {} Pokémon for trainer ID {}.",
                    team.getTeamName(), teamId, team.getTeamSize(), trainerId);
        }
        return team;
    }

    /**
     * Gets the "active" (first) team ID for a trainer. This is a convenience
     * method for commands that don't specify a team name.
     *
     * @param trainerId the trainer's database ID
     * @return the team ID, or -1 if no team exists
     */
    public int getActiveTeamId(int trainerId) {
        return teamCRUD.getActiveTeamIdForTrainer(trainerId);
    }

    /**
     * Returns all team names belonging to a trainer, for autocomplete or
     * display purposes.
     *
     * @param trainerId the trainer's database ID
     * @return list of team name strings (may be empty)
     */
    public List<String> getTeamNames(int trainerId) {
        return teamCRUD.getTeamNamesForTrainer(trainerId);
    }

    /**
     * Creates a new Pokémon, persists it, and adds it to the specified team
     * in a single coordinated operation. This is the multi-step workflow that
     * was previously split across {@code SlashExample}'s "addpokemon" handler.
     *
     * <p>Steps performed:</p>
     * <ol>
     *   <li>Check if the team is full (6 Pokémon max)</li>
     *   <li>Persist the Pokémon via {@link PokemonCRUD#createDBPokemon(Pokemon)}</li>
     *   <li>Link it to the team via {@link TeamCRUD#addPokemonToDBTeam(int, int, int)}</li>
     *   <li>Add it to the in-memory {@link Team#getPokemonList()}</li>
     * </ol>
     *
     * @param team    the team to add to (must have trainerId and teamDbId set)
     * @param pokemon the Pokémon to persist and add
     * @return the assigned slot index (0-5), or -1 on error, or -3 if the team is full
     */
    public int addPokemonToTeam(Team team, Pokemon pokemon) {
        // Check if team is full before doing any DB work
        if (team.isFull()) {
            LOGGER.warn("Team '{}' is full. Cannot add Pokémon.", team.getTeamName());
            return -3;
        }

        // Step 1: Persist the Pokémon to pokemon_instances
        int pokemonId = pokemonCRUD.createDBPokemon(pokemon);
        if (pokemonId == -1) {
            LOGGER.error("Failed to persist Pokémon '{}' to database.", pokemon.getNickname());
            return -1;
        }

        // Step 2: Link it to the team in trainer_teams
        int slotIndex = teamCRUD.addPokemonToDBTeam(team.getTrainerDbId(), pokemonId, team.getTeamDbId());
        if (slotIndex < 0) {
            LOGGER.error("Failed to add Pokémon ID {} to team '{}' for trainer ID {}.",
                    pokemonId, team.getTeamName(), team.getTrainerDbId());
            return slotIndex;
        }

        // Step 3: Update in-memory state
        team.getPokemonList().add(pokemon);
        LOGGER.info("Added '{}' to team '{}' in slot {}.", pokemon.getNickname(), team.getTeamName(), slotIndex);
        return slotIndex;
    }

    /**
     * Removes a Pokémon from a team, deletes it from the database, and
     * reorders the remaining team slots to fill the gap.
     *
     * <p>This consolidates the three separate calls that were previously in
     * {@code SlashExample}'s "releasepokemon" handler:
     * {@code getSlotIndexForPokemon()}, {@code removePokemonFromDBTeam()},
     * and {@code reorderTeamAfterRelease()}.</p>
     *
     * @param team    the team containing the Pokémon
     * @param pokemon the Pokémon to release
     * @return {@code true} if the release succeeded, {@code false} otherwise
     */
    public boolean releasePokemon(Team team, Pokemon pokemon) {
        int trainerId = team.getTrainerDbId();
        int teamId = team.getTeamDbId();

        // Find the slot this Pokémon occupies
        int slotIndex = teamCRUD.getSlotIndexForPokemon(trainerId, teamId, pokemon.getPokemonDbId());
        if (slotIndex == -1) {
            LOGGER.warn("Pokémon '{}' not found in team '{}' for trainer ID {}.",
                    pokemon.getNickname(), team.getTeamName(), trainerId);
            return false;
        }

        // Remove from DB (this also deletes the pokemon_instances row)
        boolean removed = teamCRUD.removePokemonFromDBTeam(trainerId, teamId, slotIndex);
        if (!removed) {
            LOGGER.error("Failed to remove Pokémon '{}' from team '{}'.", pokemon.getNickname(), team.getTeamName());
            return false;
        }

        // Reorder remaining slots to fill the gap
        teamCRUD.reorderTeamAfterRelease(trainerId, teamId);

        // Update in-memory state
        team.getPokemonList().remove(pokemon);

        LOGGER.info("Released '{}' from slot {} of team '{}'.", pokemon.getNickname(), slotIndex, team.getTeamName());
        return true;
    }

    /**
     * Checks whether a team has room for more Pokémon by querying the
     * database for the current slot count.
     *
     * @param trainerId the trainer's database ID
     * @param teamId    the team's database ID
     * @return {@code true} if the team has 6 Pokémon, {@code false} otherwise
     */
    public boolean isTeamFull(int trainerId, int teamId) {
        int slotCount = teamCRUD.checkSlotIndex(trainerId, teamId);
        return slotCount >= Team.MAX_TEAM_SIZE;
    }

    public Team getTeamFromName(int trainerId, String teamName) {
        return teamCRUD.getTeamByNameForTrainer(trainerId, teamName);
    }

    /**
     * Returns a Pokemon object based on pokemon nickname, assuming nicknames are unique within a team.
     * @param trainerId the trainer's database ID
     * @param teamId the team's database ID
     * @param nickname the nickname of the Pokemon to find
     * @return the Pokemon object if found, or null if not found
     */
    public Pokemon getPokemonByNickname(int trainerId, int teamId, String nickname) {
        Team loadedTeam = loadTeam(trainerId, teamId);
        if (loadedTeam == null) {
            LOGGER.warn("Team ID {} not found for trainer ID {} when searching for Pokémon by nickname.", teamId, trainerId);
            return null;
        }

        for (Pokemon pokemon : loadedTeam.getPokemonList()) {
            if (pokemon.getNickname().equalsIgnoreCase(nickname)) {
                return pokemon;
            }
        }

        LOGGER.warn("Pokémon with nickname '{}' not found in team ID {} for trainer ID {}.", nickname, teamId, trainerId);
        return null;
    }
}
