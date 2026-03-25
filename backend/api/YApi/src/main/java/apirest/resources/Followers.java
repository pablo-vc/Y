package apirest.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import apirest.config.ConnectionManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Followers REST resource.
 *
 * Base endpoint: /followers
 *
 * Provides API operations related to follower relationships between users.
 *
 * Supported operations:
 * - Follow a user
 * - Unfollow a user
 * - Check if a user follows another user
 * - Retrieve followers count
 * - Retrieve following count
 */
@Path("/followers")
public class Followers {

    /**
     * Endpoint: POST /followers/{id_follower}/follow/{id_following}
     *
     * Creates a follower relationship between two users.
     * When a user follows another user, a notification is also generated.
     *
     * The operation will fail if:
     * - The user tries to follow themselves
     * - The follower relationship already exists
     *
     * @param id_follower  ID of the user who wants to follow another user.
     * @param id_following ID of the user being followed.
     *
     * @return
     *         200 OK – Follow relationship successfully created
     *         400 BAD REQUEST – User attempted to follow themselves
     *         409 CONFLICT – The follower relationship already exists
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */
    @POST
    @Path("/{id_follower}/follow/{id_following}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response follow(@PathParam("id_follower") int id_follower,
            @PathParam("id_following") int id_following) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            if (id_follower == id_following) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            try (Connection conexion = ConnectionManager.getConnection()) {

                PreparedStatement validate = conexion.prepareStatement(
                        "SELECT 1 FROM followers WHERE id_follower=? " +
                                "AND id_following=?");
                validate.setInt(1, id_follower);
                validate.setInt(2, id_following);
                ResultSet rs = validate.executeQuery();
                if (rs.next()) {
                    return Response.status(409)
                    .entity("Following relationship already exists").build();
                }

                PreparedStatement ps = conexion.prepareStatement(
                        "INSERT INTO followers (id_follower,id_following,created_at) " +
                                "VALUES (?,?,NOW())");
                ps.setInt(1, id_follower);
                ps.setInt(2, id_following);
                ps.executeUpdate();
                PreparedStatement notify = conexion.prepareStatement(
                        "INSERT INTO notifications (id_user,id_follower,created_at) " +
                                "VALUES (?,?,NOW())");

                notify.setInt(1, id_following);
                notify.setInt(2, id_follower);

                notify.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("SQL Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }
    }

    /**
     * Endpoint: DELETE /followers/{id_follower}/unfollow/{id_following}
     *
     * Removes the follower relationship between two users.
     *
     * @param id_follower  ID of the user who wants to unfollow another user.
     * @param id_following ID of the user being unfollowed.
     *
     * @return
     *         200 OK – Successfully unfollowed the user
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */
    @DELETE
    @Path("/{id_follower}/unfollow/{id_following}")
    public Response unfollow(@PathParam("id_follower") int id_follower,
            @PathParam("id_following") int id_following) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {
                PreparedStatement ps = conexion.prepareStatement(
                        "DELETE from followers where id_follower=? " +
                                "AND id_following=?");
                ps.setInt(1, id_follower);
                ps.setInt(2, id_following);
                ps.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("SQL Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }
    }

    /**
     * Endpoint: GET /followers/isFollowing/{id_follower}/{id_following}
     *
     * Checks whether a user is following another user.
     *
     * @param id_follower  ID of the user who might be following.
     * @param id_following ID of the user being checked.
     *
     * @return
     *         -200 OK – Returns a boolean value:
     *         true if the follower relationship exists,
     *         false otherwise
     *         -500 INTERNAL SERVER ERROR – Database or server failure
     */
    @GET
    @Path("/isFollowing/{id_follower}/{id_following}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isFollowing(
            @PathParam("id_follower") int id_follower,
            @PathParam("id_following") int id_following) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {

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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }
    }

    /**
     * Endpoint: GET /followers/{id}/followersCount
     *
     * Retrieves the total number of followers for a specific user.
     *
     * @param id ID of the user whose followers count is requested.
     *
     * @return
     *         200 OK – Total number of followers
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */
    @GET
    @Path("/{id}/followersCount")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowersCount(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {

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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }
    }

    /**
     * Endpoint: GET /followers/{id}/followingCount
     *
     * Retrieves the total number of users that a specific user is following.
     *
     * @param id ID of the user whose following count is requested.
     *
     * @return
     *         200 OK – Total number of followed users
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */

    @GET
    @Path("/{id}/followingCount")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowingCount(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {

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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }
    }

}
