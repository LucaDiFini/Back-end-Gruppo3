package its.incom.webdev.rest;

import its.incom.webdev.persistence.repository.UtenteRepository;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.exception.SessionCreationException;
import its.incom.webdev.service.exception.WrongUsernameOrPasswordException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        try {
            int sessionId = authenticationService.login(email, password);
            NewCookie sessionCookie = new NewCookie("SESSION_ID", String.valueOf(sessionId));
            return Response.ok().cookie(sessionCookie).build();
        } catch (WrongUsernameOrPasswordException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Wrong username or password").build();
        } catch (SessionCreationException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating session").build();
        }
    }


/* messo tra parentesi quello precedente. La post mi fa username o password errati. Probabilmente perchè non c'è utenteService, ma almeno non mi dà errore500
    @POST
    @Path("/login")
    @Produces()
    publicResponselogin(@FormParam("email")Stringemail,@FormParam("password")Stringpassword)throwsWrongUsernameOrPasswordException,SessionCreationException{
        intsessione=0;
        sessione=authenticationService.login(email,password);
        NewCookiesessionCookie=newNewCookie.Builder("SESSION_COOKIE").value(String.valueOf(sessione)).build();
        returnResponse.ok()
                .cookie(sessionCookie)
                .build();
    }

*/
}
