package apirest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

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

@Path("/users")
public class Users {

    private static final String URL = "jdbc:mariadb://localhost:3306/y_db";
    private static final String user = "root";
    private static final String pass = "";

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong";
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest req) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {

                PreparedStatement ps = conexion.prepareStatement("SELECT * FROM users WHERE email=?");
                ps.setString(1, req.getEmail());

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
                }

                String hashedPassword = rs.getString("password");

                if (!BCrypt.checkpw(req.getPassword(), hashedPassword)) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("Contraseña incorrecta").build();
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest req) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {

                PreparedStatement check = conexion.prepareStatement("SELECT id FROM users WHERE email=?");
                check.setString(1, req.getEmail());
                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Email already in use")
                            .build();
                }
                PreparedStatement check2 = conexion.prepareStatement("SELECT id FROM users WHERE username=?");
                check2.setString(1, req.getUsername());
                ResultSet rs2 = check2.executeQuery();

                if (rs2.next()) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Username already in use")
                            .build();
                }
                String hashedPassword = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());

                PreparedStatement ps = conexion.prepareStatement(
                        "INSERT INTO users (username, email, password, bio, created_at) VALUES (?, ?, ?, '', NOW())");

                ps.setString(1, req.getUsername());
                ps.setString(2, req.getEmail());
                ps.setString(3, hashedPassword);

                ps.executeUpdate();

                return Response.status(Response.Status.CREATED).build();

            } catch (Exception e) {
                return Response.status(500).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("Select * from users");
                while (rs.next()) {
                    userList.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"),
                            rs.getString("bio"), rs.getString("created_at")));
                }
                return Response.ok(userList).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        User u;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select * from users where id=?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return Response.status(404).build();
                } else {
                    u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"),
                            rs.getString("bio"), rs.getString("created_at"));
                    return Response.ok(u).build();
                }
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement("DELETE from users where id=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @PUT
    @Path("/update/{id}")
    public Response updateUser(@PathParam("id") int id, UserUpdate update) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement check2 = conexion.prepareStatement("SELECT id FROM users WHERE username=?");
                check2.setString(1, update.getUsername());
                ResultSet rs2 = check2.executeQuery();

                if (rs2.next()) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Username already in use")
                            .build();
                }
                PreparedStatement ps = conexion.prepareStatement("UPDATE users set username=?,bio=? where id=?");
                ps.setString(1, update.getUsername());
                ps.setString(2, update.getBio());
                ps.setInt(3, id);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return Response.ok().build();
                }
                return Response.status(Response.Status.NOT_FOUND).entity("Couldn't update").build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

}
