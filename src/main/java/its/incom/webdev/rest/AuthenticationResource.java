package its.incom.webdev.rest;

import its.incom.webdev.persistence.model.CreateUtenteRequest;
import its.incom.webdev.persistence.model.Ruolo;
import its.incom.webdev.persistence.model.Utente;
import its.incom.webdev.persistence.repository.UtenteRepository;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.HashCalculator;
import its.incom.webdev.service.UtenteService;
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
    private final HashCalculator hashCalculator;

    private final UtenteService utenteService;

    public AuthenticationResource(UtenteRepository utenteRepository, AuthenticationService authenticationService, HashCalculator hashCalculator, UtenteService utenteService) {
        this.utenteRepository = utenteRepository;
        this.authenticationService = authenticationService;
        this.hashCalculator = hashCalculator;
        this.utenteService = utenteService;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        try {
            System.out.println("email: " + email + " password: " + password);
            int sessionId = authenticationService.login(email, password);
            NewCookie sessionCookie = new NewCookie("SESSION_ID", String.valueOf(sessionId));
            return Response.ok().cookie(sessionCookie).build();
        } catch (WrongUsernameOrPasswordException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Wrong username or password").build();
        } catch (SessionCreationException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating session").build();
        }
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(CreateUtenteRequest cur) {
        try {
            Utente u=utenteService.ConvertRequestToUtente(cur);
            Utente u1 = utenteRepository.createUtente(u);
            return Response.status(Response.Status.CREATED)
                    .entity(u1)
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore del server, la registrazione non è andata a buon fine")
                    .build();
        }
    }
}
