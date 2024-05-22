package its.incom.webdev.rest;

import its.incom.webdev.persistence.repository.UtenteRepository;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.exception.SessionCreationException;
import its.incom.webdev.service.exception.WrongUsernameOrPasswordException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthenticationResource {
    private final UtenteRepository utenteRepository;
    private final AuthenticationService authenticationService;

    public AuthenticationResource(UtenteRepository utenteRepository, AuthenticationService authenticationService) {
        this.utenteRepository = utenteRepository;
        this.authenticationService = authenticationService;
    }

    @POST
    @Path("/login")
    @Produces()
    public Response login(@FormParam("email") String email, @FormParam("password") String password) throws WrongUsernameOrPasswordException, SessionCreationException {
        int sessione = 0;
        sessione = authenticationService.login(email, password);
        NewCookie sessionCookie = new NewCookie.Builder("SESSION_COOKIE").value(String.valueOf(sessione)).build();
        return Response.ok()
                .cookie(sessionCookie)
                .build();
    }

}
