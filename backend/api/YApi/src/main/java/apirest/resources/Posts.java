package apirest.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import apirest.config.ConnectionManager;
import apirest.models.Post;
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
 * Post REST resource.
 *
 * Base endpoint: /posts
 *
 * Provides API operations for:
 * - Retrieving posts
 * - Retrieving posts from a specific user
 * - Retrieving posts from followed users
 * - Creating posts
 * - Deleting posts
 */
@Path("/posts")
public class Posts {

    /**
     * Endpoint: GET /posts
     *
     * Retrieves the most recent posts from the database.
     * Each post includes the username of the author.
     *
     * The results are ordered by creation date (newest first)
     * and limited to the 100 most recent posts.
     *
     * @return
     *         200 OK – List of posts
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts() {
        ArrayList<Post> postsList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT p.*, u.username FROM posts p JOIN users u ON p.id_user = u.id" +
                                "ORDER BY p.created_at DESC LIMIT 100");
                while (rs.next()) {
                    postsList.add(new Post(rs.getInt("id"), 
                                           rs.getInt("id_user"),
                                           rs.getString("content"), 
                                           rs.getString("created_at"),
                                           rs.getString("username")));
                }
                return Response.ok(postsList).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }

    }

    /**
     * Endpoint: GET /posts/{id_user}
     *
     * Retrieves all posts created by a specific user.
     *
     * The results are ordered by creation date in descending order.
     *
     * @param id_user Unique identifier of the user whose posts are requested.
     *
     * @return
     *         200 OK – List of posts created by the user
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */
    @GET
    @Path("/{id_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(@PathParam("id_user") int id_user) {
        ArrayList<Post> postsList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {
                PreparedStatement ps = conexion
                        .prepareStatement(
                                "Select p.*, u.username from posts p JOIN users u" +
                                        "ON p.id_user=u.id where p.id_user=? order by p.created_at desc");
                ps.setInt(1, id_user);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    postsList.add(new Post(rs.getInt("id"), rs.getInt("id_user"),
                            rs.getString("content"),
                            rs.getString("created_at"),
                            rs.getString("username")));
                }
                return Response.ok(postsList).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }

    }

    /**
     * Endpoint: GET /posts/following/{userId}
     *
     * Retrieves posts created by users that the specified user follows.
     *
     * This endpoint builds a feed containing posts from followed accounts,
     * ordered by creation date (newest first).
     *
     * Results are limited to the 100 most recent posts.
     *
     * @param userId ID of the user whose following feed is requested.
     *
     * @return
     *         200 OK – List of posts from followed users
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */
    @GET
    @Path("/following/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowingPosts(@PathParam("userId") int userId) {
        ArrayList<Post> postsList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {
                PreparedStatement ps = conexion.prepareStatement(
                        "SELECT p.*, u.username FROM posts p JOIN users u" +
                                "ON p.id_user = u.id JOIN followers f ON p.id_user = f.id_following" +
                                "WHERE f.id_follower = ? ORDER BY p.created_at DESC limit 100");
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    postsList.add(new Post(rs.getInt("id"), rs.getInt("id_user"),
                            rs.getString("content"),
                            rs.getString("created_at"),
                            rs.getString("username")));
                }
                return Response.ok(postsList).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }

    }

    /**
     * Endpoint: POST /posts/create
     *
     * Creates a new post in the system.
     *
     * The post content and the ID of the user creating the post must be provided
     * in the request body. The creation date is automatically generated.
     *
     * @param p Post object containing the user ID and post content.
     *
     * @return
     *         200 OK – Post successfully created
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(Post p) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {
                PreparedStatement ps = conexion
                        .prepareStatement("INSERT INTO posts (id_user, content, created_at)" +
                                "VALUES (?, ?, NOW())");
                ps.setInt(1, p.getId_user());
                ps.setString(2, p.getContent());
                ps.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }
    }

    /**
     * Endpoint: DELETE /posts/delete/{id}
     *
     * Deletes a post from the database.
     *
     * @param id Unique identifier of the post to delete.
     *
     * @return
     *         200 OK – Post successfully deleted
     *         500 INTERNAL SERVER ERROR – Database or server failure
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deletePost(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {
                PreparedStatement ps = conexion
                        .prepareStatement("DELETE from posts where id=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Driver not found").build();
        }
    }

}
