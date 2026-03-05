package apirest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/posts")
public class Posts {

    private static final String URL = "jdbc:mariadb://localhost:3306/y_db";
    private static final String user = "root";
    private static final String pass = "";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts() {
        ArrayList<Post> postsList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT p.*, u.username FROM posts p JOIN users u ON p.id_user = u.id ORDER BY p.created_at DESC LIMIT 100");
                while (rs.next()) {
                    postsList.add(new Post(rs.getInt("id"), rs.getInt("id_user"),
                            rs.getString("content"), rs.getString("created_at"),
                            rs.getString("username")));
                }
                return Response.ok(postsList).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }

    }

    @GET
    @Path("/{id_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(@PathParam("id_user") int id_user) {
        ArrayList<Post> postsList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement("Select p.*, u.username from posts p JOIN users u ON p.id_user=u.id where p.id_user=? order by p.created_at desc");
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
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }

    }

    @GET
    @Path("/following/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowingPosts(@PathParam("userId") int userId) {
        ArrayList<Post> postsList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement(
                        "SELECT p.*, u.username FROM posts p JOIN users u ON p.id_user = u.id JOIN followers f ON p.id_user = f.id_following WHERE f.id_follower = ? ORDER BY p.created_at DESC limit 100");
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
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }

    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(Post p) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement("INSERT INTO posts (id_user, content, created_at) VALUES (?, ?, NOW())");
                ps.setInt(1, p.getId_user());
                ps.setString(2, p.getContent());
                ps.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deletePost(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement("DELETE from posts where id=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

}
