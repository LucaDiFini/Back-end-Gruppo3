package its.incom.webdev.rest;

import its.incom.webdev.persistence.model.CreateUtenteResponse;
import its.incom.webdev.persistence.repository.CandidaturaRepository;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.exception.WrongUsernameOrPasswordException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/candidatura")
public class CandidaturaResource {
    AuthenticationService authenticationService;
    CandidaturaRepository candidaturaRepository;

    public CandidaturaResource(AuthenticationService authenticationService, CandidaturaRepository candidaturaRepository) {
        this.authenticationService = authenticationService;
        this.candidaturaRepository = candidaturaRepository;
    }

    @POST
    @Path("/iscrizione/{corsoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setCandidatura(@CookieParam("SESSION_ID") @DefaultValue("-1") int sessioneId, @PathParam("corsoId") int corsoId) throws WrongUsernameOrPasswordException {
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
                            candidaturaRepository.createCandidatura(cur.getId(),corsoId))
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore del server, la candidatura non è andata a buon fine")
                    .build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCandidature() throws SQLException {
        return Response.status(Response.Status.OK)
                .entity(candidaturaRepository.getCandidature())
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCandidature(@PathParam("id") int id) throws SQLException {
        return Response.status(Response.Status.OK)
                .entity(candidaturaRepository.getCandidatura(id))
                .build();
    }

}
