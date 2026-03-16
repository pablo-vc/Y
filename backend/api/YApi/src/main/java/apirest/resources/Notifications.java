package apirest.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import apirest.models.Notification;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Notification REST resource.
 *
 * Base endpoint: /notifications
 *
 * Provides API operations related to user notifications.
 * Currently supports retrieving notifications generated
 * when other users follow a specific user.
 */
@Path("/notifications")
public class Notifications {
    private static final String URL = "jdbc:mariadb://localhost:3306/y_db";
    private static final String user = "root";
    private static final String pass = "";

    /**
     * Endpoint: GET /notifications/{id_user}
     *
     * Retrieves the list of notifications for a specific user.
     *
     * The results are ordered by creation date in descending order
     * (most recent notifications first).
     *
     * @param id_user Unique identifier of the user whose notifications are
     *                requested.
     *
     * @return
     *         200 OK – List of notifications
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */
    @GET
    @Path("/{id_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserNotifications(@PathParam("id_user") int id_user) {

        ArrayList<Notification> list = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {

                PreparedStatement ps = conexion.prepareStatement(
                        "SELECT n.id, n.id_user, n.id_follower, n.created_at, u.username FROM notifications n JOIN users u ON n.id_follower = u.id WHERE n.id_user = ? ORDER BY n.created_at DESC");

                ps.setInt(1, id_user);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Notification(
                            rs.getInt("id"),
                            rs.getInt("id_user"),
                            rs.getInt("id_follower"),
                            rs.getString("username"),
                            rs.getString("created_at")));
                }

                return Response.ok(list).build();

            } catch (Exception e) {
                return Response.status(500).entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

}
