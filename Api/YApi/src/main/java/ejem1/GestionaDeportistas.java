package ejem1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import org.mariadb.jdbc.export.Prepare;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.scene.media.Media;

@Path("/deportistas")
public class GestionaDeportistas {

    private static final String URL = "jdbc:mariadb://localhost:3306/ad_tema6";
    private static final String user = "root";
    private static final String pass = "";

    private ArrayList<Deportista> deportistas = new ArrayList<>();

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response obtenerTodos() {
        ArrayList<Deportista> listaDeportistas = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass);
                    Statement st = conexion.createStatement();
                    ResultSet rs = st.executeQuery("Select * from deportistas")) {
                while (rs.next()) {
                    listaDeportistas.add(new Deportista(rs.getInt("id"), rs.getString("nombre"),
                            rs.getBoolean("activo"), rs.getString("deporte"), rs.getString("genero")));
                }
                return Response.ok(listaDeportistas).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }

    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response buscarJugador(@PathParam("id") int id) {
        Deportista d;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select * from deportistas where id=?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                d = new Deportista(rs.getInt("id"), rs.getString("nombre"),
                        rs.getBoolean("activo"), rs.getString("deporte"), rs.getString("genero"));
                return Response.ok(d).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Path("/deporte/{nombreDeporte}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response porDeporte(@PathParam("deporte") String deporte) {
        ArrayList<Deportista> deportistas = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select * from deportistas where deporte=?");
                ps.setString(1, deporte);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    deportistas.add(new Deportista(rs.getInt("id"), rs.getString("nombre"),
                            rs.getBoolean("activo"), rs.getString("deporte"), rs.getString("genero")));
                }
                return Response.ok(deportistas).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }

    }

    @GET
    @Path("/activos")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response activos() {
        ArrayList<Deportista> deportistas = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select * from deportistas where activo=?");
                ps.setBoolean(1, true);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    deportistas.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                            rs.getString("deporte"), rs.getString("genero")));
                }
                return Response.ok(deportistas).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }

    }

    @GET
    @Path("/retirados")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response retirados() {
        ArrayList<Deportista> deportistas = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select * from deportistas where activo=?");
                ps.setBoolean(1, false);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    deportistas.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                            rs.getString("deporte"), rs.getString("genero")));
                }
                return Response.ok(deportistas).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }

    }

    @GET
    @Path("/masculinos")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response masculinos() {
        ArrayList<Deportista> deportistas = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select * from deportistas where genero=?");
                ps.setString(1, "masculino");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    deportistas.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                            rs.getString("deporte"), rs.getString("genero")));
                }
                return Response.ok(deportistas).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra en driver").build();
        }
    }

    @GET
    @Path("/femeninos")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response femeninos() {
        ArrayList<Deportista> deportistas = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select * from deportistas where genero=?");
                ps.setString(1, "femenino");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    deportistas.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                            rs.getString("deporte"), rs.getString("genero")));
                }
                return Response.ok(deportistas).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra en driver").build();
        }
    }

    @GET
    @Path("/xg")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deportesPorGenero() {
        ArrayList<Deportista>[] listas = new ArrayList[2];
        listas[0] = new ArrayList<>();
        listas[1] = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select * from deportistas");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getString("genero").equals("masculino")) {
                        listas[0].add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                                rs.getString("deporte"), rs.getString("genero")));
                    } else {
                        listas[1].add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                                rs.getString("deporte"), rs.getString("genero")));
                    }
                }
                return Response.ok(listas).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Path("/deporte/{nombreDeporte}/activos")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response activosDeporte(@PathParam("deporte") String deporte) {
        ArrayList<Deportista> activos = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement("Select * from deportistas where deporte=? and activo=?");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    activos.add(new Deportista(rs.getInt("id"), rs.getString("nombre"), rs.getBoolean("activo"),
                            rs.getString("deporte"), rs.getString("genero")));
                }
                return Response.ok(activos).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Path("/sdepor")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response contarDeportistas() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select count(id) from deportistas");
                ResultSet rs = ps.executeQuery();
                return Response.ok(rs.getInt(1)).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Path("/deportes")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deportes() {
        ArrayList<String> nombres = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("Select distinct deporte from deportistas order desc");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    nombres.add(rs.getString(1));
                }
                return Response.ok(nombres).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response crearDeportista(Deportista d) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement("insert into deportistas (nombre,activo,deporte,genero) values(?,?,?,?)");
                ps.setString(1, d.getNombre());
                ps.setBoolean(2, d.isActivo());
                ps.setString(3, d.getDeporte());
                ps.setString(4, d.getGenero());
                ps.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response crearDeportistaForm(@FormParam("nombre") String nombre, @FormParam("activo") boolean activo,
            @FormParam("deporte") String deporte, @FormParam("genero") String genero) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement("insert into deportistas (nombre,activo,deporte,genero) values(?,?,?,?)");
                ps.setString(1, nombre);
                ps.setBoolean(2, activo);
                ps.setString(3, deporte);
                ps.setString(4, genero);
                ps.executeUpdate();
                return Response.ok().build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    // @POST
    // @Path("/adds")
    // @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    // public Response crearDeportistas(ArrayList<Deportista> deps) {

    // }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response actualizar(Deportista d) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement("update deportistas set nombre=?,activo=?,deporte=?,genero=? where id=?");
                ps.setString(1, d.getNombre());
                ps.setBoolean(2, d.isActivo());
                ps.setString(3, d.getDeporte());
                ps.setString(4, d.getGenero());
                ps.setInt(5, d.getId());
                int n = ps.executeUpdate();
                return Response.ok().entity("filas afectadas: " + n).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @DELETE
    @Path("/del/{id}")
    public Response borrar(@PathParam("id") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion.prepareStatement("delete from deportistas where id=?");
                ps.setInt(1, id);
                int n = ps.executeUpdate();
                return Response.ok().entity("filas afectadas: " + n).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    @GET
    @Path("/del/{id}/{num}")
    @Produces("image/jpg")
    public Response imagenDeportista(@PathParam("id") int id, @PathParam("num") int num) throws FileNotFoundException {
        String ruta = "";
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, user, pass)) {
                PreparedStatement ps = conexion
                        .prepareStatement(
                                "select nombre from images where id=? and nombre like '?_?_%'");
                ps.setInt(1, id);
                ps.setInt(2, id);
                ps.setInt(3, num);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ruta = "C:\\Users\\User\\Desktop\\2ºDAM\\AD\\Tema5\\tema5maven\\" + rs.getString("nombre");
                }
                File f = new File(ruta);
                FileInputStream fis = new FileInputStream(f);
                return Response.ok(fis).build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    // @POST
    // @Path("/android")
    // @Consumes(MediaType.APPLICATION_JSON)
    // public Response subirDeportistaAndroid(Deportista d) throws
    // ClassNotFoundException {
    // try {
    // Class.forName("org.mariadb.jdbc.Driver");
    // Statement st = conexion.createStatement();
    // st.executeUpdate(
    // "INSERT INTO deportistas(nombre,deporte) values ('" + d.getNombre() + ",'" +
    // d.getDeporte() + "')");
    // return Response.ok().build();
    // } catch (SQLException e) {
    // return
    // Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
    // }
    // }

}
