package apirest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/notifications")
public class Notifications {
    private static final String URL = "jdbc:mariadb://localhost:3306/y_db";
    private static final String user = "root";
    private static final String pass = "";

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
