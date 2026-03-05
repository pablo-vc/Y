package apirest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/followers")
public class Followers {

    private static final String URL = "jdbc:mariadb://localhost:3306/y_db";
    private static final String user = "root";
    private static final String pass = "";

    @POST
    @Path("/{id_follower}/follow/{id_following}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response follow(@PathParam("id_follower") int id_follower, @PathParam("id_following") int id_following) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            if (id_follower == id_following) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {

                PreparedStatement validate = conexion
                        .prepareStatement("SELECT 1 FROM followers WHERE id_follower=? AND id_following=?");
                validate.setInt(1, id_follower);
                validate.setInt(2, id_following);
                ResultSet rs = validate.executeQuery();
                if (rs.next()) {
                    return Response.status(409).entity("Ya sigues a este usuario").build();
                }

                PreparedStatement ps = conexion
                        .prepareStatement(
                                "INSERT INTO followers (id_follower,id_following,created_at) values (?,?,NOW())");
                ps.setInt(1, id_follower);
                ps.setInt(2, id_following);
                ps.executeUpdate();
                PreparedStatement notif = conexion.prepareStatement(
                        "INSERT INTO notifications (id_user,id_follower,created_at) VALUES (?,?,NOW())");

                notif.setInt(1, id_following);
                notif.setInt(2, id_follower);

                notif.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("SQL Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @DELETE
    @Path("/{id_follower}/unfollow/{id_following}")
    public Response unfollow(@PathParam("id_follower") int id_follower, @PathParam("id_following") int id_following) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement("DELETE from followers where id_follower=? and id_following=?");
                ps.setInt(1, id_follower);
                ps.setInt(2, id_following);
                ps.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("SQL Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Path("/isFollowing/{id_follower}/{id_following}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isFollowing(
            @PathParam("id_follower") int id_follower,
            @PathParam("id_following") int id_following) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {

                PreparedStatement ps = conexion.prepareStatement(
                        "SELECT 1 FROM followers WHERE id_follower = ? AND id_following = ?");
                ps.setInt(1, id_follower);
                ps.setInt(2, id_following);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return Response.ok(true).build();
                } else {
                    return Response.ok(false).build();
                }

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Path("/{id}/followersCount")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowersCount(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {

                PreparedStatement ps = conexion.prepareStatement(
                        "SELECT COUNT(*) as total FROM followers WHERE id_following = ?");
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int total = rs.getInt("total");
                    return Response.ok(total).build();
                }

                return Response.ok(0).build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Path("/{id}/followingCount")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowingCount(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {

                PreparedStatement ps = conexion.prepareStatement(
                        "SELECT COUNT(*) as total FROM followers WHERE id_follower = ?");
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int total = rs.getInt("total");
                    return Response.ok(total).build();
                }

                return Response.ok(0).build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

}
