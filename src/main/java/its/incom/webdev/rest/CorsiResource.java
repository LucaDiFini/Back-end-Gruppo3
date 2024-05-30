package its.incom.webdev.rest;

import its.incom.webdev.persistence.exception.UserNotHavePermissionException;
import its.incom.webdev.rest.model.CreateCorsoRequest;
import its.incom.webdev.persistence.repository.CandidaturaRepository;
import its.incom.webdev.persistence.repository.CorsoRepository;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.CorsiService;
import its.incom.webdev.service.UtenteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/corsi")
public class CorsiResource {
    private final CorsoRepository corsoRepository;
    private final AuthenticationService authenticationService;
    private final CandidaturaRepository candidaturaRepository;

    private final UtenteService utenteService;

    private CorsiService corsiService;

    public CorsiResource(CorsoRepository corsoRepository, AuthenticationService authenticationService, CandidaturaRepository candidaturaRepository, UtenteService utenteService, CorsiService corsiService) {
        this.corsoRepository = corsoRepository;
        this.authenticationService = authenticationService;

        this.candidaturaRepository = candidaturaRepository;
        this.utenteService = utenteService;

        this.corsiService = corsiService;
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

    @POST
    @Path("/nuovo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newCorso(@CookieParam("SESSION_ID") @DefaultValue("-1") int sessioneId, CreateCorsoRequest request) throws UserNotHavePermissionException, SQLException {
        if (!utenteService.isAdmin(sessioneId)) {
            throw new UserNotHavePermissionException("l'utente non ha i permessi");
        }
        return Response.status(Response.Status.CREATED)
                .entity(corsiService.newCorso(request))
                .build();
    }
}
