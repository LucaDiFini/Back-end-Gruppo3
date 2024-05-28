package its.incom.webdev.rest;

import its.incom.webdev.persistence.model.CreateUtenteResponse;
import its.incom.webdev.persistence.repository.CandidaturaRepository;
import its.incom.webdev.persistence.repository.CorsoRepository;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.exception.WrongUsernameOrPasswordException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/corsi")
public class CorsiResource {
    private final CorsoRepository corsoRepository;
    private final AuthenticationService authenticationService;
    private final CandidaturaRepository candidaturaRepository;

    public CorsiResource(CorsoRepository corsoRepository, AuthenticationService authenticationService, CandidaturaRepository candidaturaRepository) {
        this.corsoRepository = corsoRepository;
        this.authenticationService = authenticationService;

        this.candidaturaRepository = candidaturaRepository;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsoById(@PathParam("id") int id) throws SQLException {

        return Response.status(Response.Status.OK)
                .entity(corsoRepository.getCorsoById(id))
                .build();
    }

    @GET
    @Path("/tipologia/{categoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsoByCategoria(@PathParam("categoria") String categoria) {
        System.out.println("ciao");
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
