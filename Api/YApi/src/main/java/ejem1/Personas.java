package ejem1;

import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/personas")
public class Personas {
    ArrayList<Persona> personas;

    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response guardar(Persona p) {
        personas.add(p);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Persona> listar() {
        return personas;
    }

    @GET
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ver(@PathParam("nombre") String nombre) {
        for (Persona p : personas) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return Response.ok(p).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@DefaultValue("")@QueryParam("param") String texto) {
        for (Persona p : personas) {
            if (p.getNombre().equalsIgnoreCase(texto)) {
                return Response.ok(p).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/form")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response guardarForm(@FormParam("id") int id, @FormParam("nombre") String nombre,
            @FormParam("casado") boolean casado, @FormParam("sexo") String sexo) {
        personas.add(new Persona(id, nombre, casado, sexo));
        return Response.ok().build();
    }

    @POST
    @Path("/add")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response add(ArrayList<Persona> lista) {
        personas.addAll(lista);
        return Response.ok().build();
    }

    @DELETE
    @Path("/id/{id}")
    public Response borrar(@PathParam("id") int id) {
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).getId() == id) {
                personas.remove(i);
                return Response.ok().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/XML")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response verXML(@PathParam("id")int id){
        for (Persona p : personas) {
            if (p.getId()==id) {
                return Response.ok(p).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
