package its.incom.webdev.rest;

import its.incom.webdev.persistence.model.CreateUtenteRequest;

import its.incom.webdev.persistence.model.CreateUtenteResponse;
import its.incom.webdev.persistence.model.Utente;
import its.incom.webdev.persistence.repository.CorsoRepository;
import its.incom.webdev.persistence.repository.UtenteRepository;
import its.incom.webdev.service.AuthenticationService;
import its.incom.webdev.service.HashCalculator;
import its.incom.webdev.service.UtenteService;
import its.incom.webdev.service.exception.SessionCreationException;
import its.incom.webdev.service.exception.WrongUsernameOrPasswordException;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.Optional;


@Path("/auth")
public class AuthenticationResource {
    private final UtenteRepository utenteRepository;
    private final AuthenticationService authenticationService;


    private final UtenteService utenteService;

    public AuthenticationResource(UtenteRepository utenteRepository, AuthenticationService authenticationService, UtenteService utenteService) {
        this.utenteRepository = utenteRepository;
        this.authenticationService = authenticationService;
        this.utenteService = utenteService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(JsonObject loginRequest) {
        String email = loginRequest.getString("email");
        String password = loginRequest.getString("password");
        try {
            int sessionId = authenticationService.login(email, password);
            NewCookie sessionCookie = new NewCookie("SESSION_ID", String.valueOf(sessionId));
            //prendi utente da sessione Id

            //
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
            // Controlla se esiste già un utente con la stessa email
            Optional<Utente> existingUser = utenteRepository.findByEmail(cur.getEmail());
            if (existingUser.isPresent()) {
                // Se esiste, restituisci un messaggio JSON appropriato
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"Utente già presente\"}")
                        .build();
            }

            // Se non esiste, crea un nuovo utente
            Utente u = utenteService.ConvertRequestToUtente(cur);
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
