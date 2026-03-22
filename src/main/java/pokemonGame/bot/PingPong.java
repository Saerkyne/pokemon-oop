package pokemonGame.bot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pokemonGame.db.DatabaseSetup;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import java.util.logging.Logger;
import java.sql.*;

public class PingPong extends ListenerAdapter{

    private static final Logger LOGGER = Logger.getLogger(PingPong.class.getName());

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        String user = event.getAuthor().getName();
        long userId = event.getAuthor().getIdLong();
        createUser(userId, user); // Store the user in the database
        LOGGER.log(java.util.logging.Level.INFO, "Received message: '" + content + "' from user: " + user + " (ID: " + userId + ")");

        if (content.equals("!ping")) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue();
        }
    }

    public int createUser(long discordID, String discordUsername) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO pingtest (discord_id, discord_username) VALUES (?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setLong(1, discordID);
                pstmt.setString(2, discordUsername);

                pstmt.executeUpdate();
                LOGGER.log(java.util.logging.Level.INFO, "User '" + discordUsername + "' created successfully with Discord ID: " + discordID);

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        LOGGER.log(java.util.logging.Level.INFO, "Generated user ID: " + userId);
                        return userId; // Return the generated user ID
                    } else {
                        LOGGER.log(java.util.logging.Level.SEVERE, "Failed to retrieve generated user ID for Discord ID: " + discordID);
                        return -1; // Return -1 to indicate an error occurred
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Error creating user with Discord ID: " + discordID + " and username: " + discordUsername, e);
            return -1; // Return -1 to indicate an error occurred
        }
        
    }

}
