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

    @POST
    @Path("/candidatura/{corsoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setCandidatura(@CookieParam("SESSION_ID") @DefaultValue("-1") int sessioneId,@PathParam("corsoId") int corsoId) throws WrongUsernameOrPasswordException {
        //controllare se l'utente è loggato
        if (sessioneId == -1) {
            //eccezione personalizzata notLogged
            throw new WrongUsernameOrPasswordException();
        }
        try {
            //prendere l'id del utente
            CreateUtenteResponse cur =authenticationService.getProfile(sessioneId);
            //creare iscrizione con id utente e id corso

            return Response.status(Response.Status.CREATED)
                    .entity(
                            //errore qui
                            candidaturaRepository.createCandidatura(cur.getId(),corsoId))
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore del server, la candidatura non è andata a buon fine")
                    .build();
        }
    }
}
