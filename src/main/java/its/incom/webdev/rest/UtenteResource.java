package its.incom.webdev.rest;

import its.incom.webdev.persistence.model.CandidaturaResponse;
import its.incom.webdev.persistence.model.CreateUtenteResponse;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.CandidatureService;
import its.incom.webdev.service.exception.WrongUsernameOrPasswordException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/profile")
public class UtenteResource {
    private final AuthenticationService authenticationService;
    private final CandidatureService candidatureService;

    public UtenteResource(AuthenticationService authenticationService, CandidatureService candidatureService) {
        this.authenticationService = authenticationService;
        this.candidatureService = candidatureService;
    }

    @GET
    @Path("")
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
}
