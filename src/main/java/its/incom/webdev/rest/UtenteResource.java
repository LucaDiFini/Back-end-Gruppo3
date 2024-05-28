package its.incom.webdev.rest;

import its.incom.webdev.persistence.model.CreateUtenteResponse;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.exception.WrongUsernameOrPasswordException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/profile")
public class UtenteResource {
    private AuthenticationService authenticationService;

    public UtenteResource(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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
    public CreateUtenteResponse getCandidature(@CookieParam("SESSION_ID") @DefaultValue("-1") int sessionId) throws WrongUsernameOrPasswordException, SQLException {
        if (sessionId == -1) {
            //eccezione personalizzata notLogged
            throw new WrongUsernameOrPasswordException();
        }
        return authenticationService.getProfile(sessionId);
    }
}
