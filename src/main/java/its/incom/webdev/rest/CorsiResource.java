package its.incom.webdev.rest;

import its.incom.webdev.persistence.repository.CorsoRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/corsi")
public class CorsiResource {
    private final CorsoRepository corsoRepository;

    public CorsiResource(CorsoRepository corsoRepository) {
        this.corsoRepository = corsoRepository;
    }

    @GET
    @Path("{categoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsoByCategoria(@PathParam("categoria") String categoria) {

        return Response.status(Response.Status.OK)
                .entity(corsoRepository.getCorsiByCategoria(categoria))
                .build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsi() throws SQLException {
        return Response.status(Response.Status.OK)
                .entity(corsoRepository.getCorsi())
                .build();
    }
}
