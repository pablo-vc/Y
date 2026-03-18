package apirest.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import apirest.config.ConnectionManager;
import apirest.dto.LoginRequest;
import apirest.dto.RegisterRequest;
import apirest.dto.UserUpdate;
import apirest.models.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Resource class that exposes REST endpoints for managing users.
 * 
 * Base path: /users
 * 
 * This class provides operations for:
 * - User authentication
 * - User registration
 * - Retrieving users
 * - Updating user profiles
 * - Deleting users
 */
@Path("/users")
public class Users {

    /**
     * Endpoint: GET /users/ping
     *
     * Simple health check endpoint used to verify that the API is running.
     *
     * @return "pong" if the service is available.
     */
    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong";
    }

    /**
     * Endpoint: POST /users/login
     *
     * Authenticates a user using email and password.
     * The password is verified using BCrypt hashing.
     *
     * @param req Login request containing email and password.
     *
     * @return
     *         200 OK – Authenticated user data
     *         401 UNAUTHORIZED – Incorrect password
     *         404 NOT FOUND – User does not exist
     *         500 INTERNAL SERVER ERROR – Server failure
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest req) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {

                PreparedStatement ps = conexion.prepareStatement(
                        "SELECT * FROM users WHERE email=?");
                ps.setString(1, req.getEmail());

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Usuario no encontrado").build();
                }

                String hashedPassword = rs.getString("password");

                if (!BCrypt.checkpw(req.getPassword(), hashedPassword)) {
                    return Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Contraseña incorrecta").build();
                }

                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("bio"),
                        rs.getString("created_at"));

                return Response.ok(user).build();

            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("No encuentra el driver").build();
        }
    }

    /**
     * Endpoint: POST /users/register
     *
     * Creates a new user account.
     * The password is hashed using BCrypt before storing it in the database.
     *
     * @param req Registration request containing username, email and password.
     *
     * @return
     *         201 CREATED – User successfully registered
     *         409 CONFLICT – Email or username already exists
     *         500 INTERNAL SERVER ERROR – Server failure
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest req) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {

                PreparedStatement check = conexion.prepareStatement(
                        "SELECT id FROM users WHERE email=?");
                check.setString(1, req.getEmail());
                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Email already in use")
                            .build();
                }
                PreparedStatement check2 = conexion.prepareStatement(
                        "SELECT id FROM users WHERE username=?");
                check2.setString(1, req.getUsername());
                ResultSet rs2 = check2.executeQuery();

                if (rs2.next()) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Username already in use")
                            .build();
                }
                String hashedPassword = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());

                PreparedStatement ps = conexion.prepareStatement(
                        "INSERT INTO users (username, email, password, bio, created_at)" +
                                "VALUES (?, ?, ?, '', NOW())");

                ps.setString(1, req.getUsername());
                ps.setString(2, req.getEmail());
                ps.setString(3, hashedPassword);

                ps.executeUpdate();

                return Response.status(Response.Status.CREATED).build();

            } catch (Exception e) {
                return Response.status(500).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("No encuentra el driver").build();
        }
    }

    /**
     * Endpoint: GET /users
     *
     * Retrieves all users stored in the database.
     *
     * @return
     *         200 OK – List of users
     *         500 INTERNAL SERVER ERROR – Database failure
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("Select * from users");
                while (rs.next()) {
                    userList.add(new User(rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("bio"),
                            rs.getString("created_at")));
                }
                return Response.ok(userList).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("No encuentra el driver").build();
        }
    }

    /**
     * Endpoint: GET /users/{id}
     *
     * Retrieves a specific user by their ID.
     *
     * @param id Unique identifier of the user.
     *
     * @return
     *         200 OK – User found
     *         404 NOT FOUND – User does not exist
     *         500 INTERNAL SERVER ERROR – Server failure
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        User u;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {
                PreparedStatement ps = conexion.prepareStatement(
                        "Select * from users where id=?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return Response.status(404).build();
                } else {
                    u = new User(rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("bio"),
                            rs.getString("created_at"));

                    return Response.ok(u).build();
                }
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Database error").build();

            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("No encuentra el driver").build();
        }
    }

    /**
     * Endpoint: DELETE /users/{id}
     *
     * Deletes a user from the database.
     *
     * @param id Unique identifier of the user to delete.
     *
     * @return
     *         200 OK – User deleted
     *         500 INTERNAL SERVER ERROR – Server failure
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {
                PreparedStatement ps = conexion
                        .prepareStatement("DELETE from users where id=?");
                ps.setInt(1, id);
                ps.executeUpdate();

                return Response.ok().build();

            } catch (SQLException e) {

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Database error").build();

            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("No encuentra el driver").build();
        }
    }

    /**
     * Endpoint: PUT /users/{id}
     *
     * Updates the username and bio of a user.
     * The endpoint verifies that the new username is not already used by another
     * user.
     *
     * @param id     ID of the user to update.
     * @param update Object containing the new username and bio.
     *
     * @return
     *         200 OK – User updated
     *         404 NOT FOUND – User not found
     *         409 CONFLICT – Username already exists
     *         500 INTERNAL SERVER ERROR – Server failure
     */
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, UserUpdate update) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = ConnectionManager.getConnection()) {

                if (usernameInUse(conexion, update.getUsername(), id)) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Username already in use")
                            .build();
                }
                if (emailInUse(conexion, update.getEmail(), id)) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Email already in use")
                            .build();
                }

                PreparedStatement ps = conexion.prepareStatement(
                        "UPDATE users set username=?,email=?,bio=? where id=?");
                ps.setString(1, update.getUsername());
                ps.setString(2, update.getEmail());
                ps.setString(3, update.getBio());
                ps.setInt(4, id);
                int rs = ps.executeUpdate();
                System.out.println("Filas actualizadas: " + rs);
                if (rs > 0) {
                    return Response.ok().build();
                }
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Database error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("No encuentra el driver").build();
        }
    }

    private Boolean usernameInUse(Connection conexion, String username, int id) throws SQLException {
        PreparedStatement ps = conexion.prepareStatement(
                "SELECT id FROM users WHERE username=? and id!=?");
        ps.setString(1, username);
        ps.setInt(2, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private Boolean emailInUse(Connection conexion, String email, int id) throws SQLException {
        PreparedStatement ps = conexion.prepareStatement(
                "SELECT id FROM users WHERE email=? and id!=?");
        ps.setString(1, email);
        ps.setInt(2, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

}
