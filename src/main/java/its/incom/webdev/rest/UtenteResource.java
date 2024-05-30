package its.incom.webdev.rest;

import its.incom.webdev.persistence.exception.UserNotHavePermissionException;
import its.incom.webdev.rest.model.CandidaturaResponse;
import its.incom.webdev.rest.model.ChangeRuoloRequest;
import its.incom.webdev.rest.model.CreateUtenteResponse;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.CandidatureService;
import its.incom.webdev.service.UtenteService;
import its.incom.webdev.service.exception.WrongUsernameOrPasswordException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/utente")
public class UtenteResource {
    private final AuthenticationService authenticationService;
    private final CandidatureService candidatureService;

    private final UtenteService utenteService;

    public UtenteResource(AuthenticationService authenticationService, CandidatureService candidatureService, UtenteService utenteService) {
        this.authenticationService = authenticationService;
        this.candidatureService = candidatureService;
        this.utenteService = utenteService;
    }

    @GET
    @Path("/profile")
    public CreateUtenteResponse getProfile(@CookieParam("SESSION_ID") @DefaultValue("-1") int sessionId) throws WrongUsernameOrPasswordException, SQLException {
        if (sessionId == -1) {
            //eccezione personalizzata notLogged
            throw new WrongUsernameOrPasswordException();
        }
        return authenticationService.getProfile(sessionId);
    }
    @GET
    @Path("/candidature")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CandidaturaResponse> getCandidature(@CookieParam("SESSION_ID") @DefaultValue("-1") int sessioneId) throws WrongUsernameOrPasswordException, SQLException {
        if (sessioneId == -1) {
            //eccezione personalizzata notLogged
            throw new WrongUsernameOrPasswordException();
        }
        //dalla sessione prendo id utente
        CreateUtenteResponse cur =authenticationService.getProfile(sessioneId);


        //dovrei collegare con corsi per le date
        //cerco le candidature dell'utente dal suo id


        return candidatureService.getCandidatureOfUtente(cur.getId());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUtenti(@CookieParam("SESSION_ID") @DefaultValue("-1") int sessioneId) throws SQLException, WrongUsernameOrPasswordException, UserNotHavePermissionException {
        if (sessioneId == -1) {
            //eccezione personalizzata notLogged
            throw new WrongUsernameOrPasswordException();
        }

        if (!utenteService.isAdmin(sessioneId)) {
            throw new UserNotHavePermissionException("l'utente non ha i permessi");
        }

        return Response.status(Response.Status.OK)
                .entity(utenteService.getAllUtenti())
                .build();
    }

    @PUT
    @Path("/changeRuolo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeRuolo(@CookieParam("SESSION_ID") @DefaultValue("-1") int sessioneId, ChangeRuoloRequest request) throws WrongUsernameOrPasswordException, UserNotHavePermissionException {
        if (sessioneId == -1) {
            //eccezione personalizzata notLogged
            throw new WrongUsernameOrPasswordException();
        }

        if (!utenteService.isAdmin(sessioneId)) {
            throw new UserNotHavePermissionException("l'utente non ha i permessi");
        }
        utenteService.setRuoloOfUser(request.getId_utente(), request.getNew_ruolo());
        return Response.status(Response.Status.ACCEPTED)
                .build();
    }
}
