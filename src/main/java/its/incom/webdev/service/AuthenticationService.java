package its.incom.webdev.service;

import its.incom.webdev.persistence.model.CreateUtenteResponse;
import its.incom.webdev.persistence.model.Utente;
import its.incom.webdev.persistence.model.Sessione;
import its.incom.webdev.persistence.repository.UtenteRepository;
import its.incom.webdev.persistence.repository.SessioneRepository;
import its.incom.webdev.service.exception.SessionCreationException;
import its.incom.webdev.service.exception.WrongUsernameOrPasswordException;

import jakarta.enterprise.context.ApplicationScoped;

import java.sql.SQLException;
import java.util.Optional;

@ApplicationScoped
public class AuthenticationService {

    private final UtenteRepository utenteRepository;
    private final UtenteService utenteService;
    private final HashCalculator hashCalculator;
    private final SessioneRepository sessioneRepository;

    // Costruttore con iniezione delle dipendenze
    public AuthenticationService(
            UtenteRepository utenteRepository,
            UtenteService utenteService,
            HashCalculator hashCalculator,
            SessioneRepository sessioneRepository
    ) {
        this.utenteRepository = utenteRepository;
        this.utenteService = utenteService;
        this.hashCalculator = hashCalculator;
        this.sessioneRepository = sessioneRepository;
    }

    // Metodo per effettuare il login
    public int login(String email, String password) throws WrongUsernameOrPasswordException, SessionCreationException {
        // Calcola l'hash della password
        String hash = hashCalculator.calculateHash(password);
        System.out.println("psw hash "+hash);
        // Cerca l'utente nel database
        Optional<Utente> maybePartecipante = utenteRepository.findByEmailPsw(email, hash);
        if (maybePartecipante.isPresent()) {
            Utente p = maybePartecipante.get();
            try {
                // Crea una nuova sessione per l'utente
                int sessione = sessioneRepository.insertSessione(p.getId());
                return sessione;
            } catch (SQLException e) {
                // Gestisce le eccezioni durante la creazione della sessione
                throw new SessionCreationException(e);
            }
        } else {
            // Lancia un'eccezione se l'utente non esiste
            throw new WrongUsernameOrPasswordException();
        }
    }

    // Metodo per effettuare il logout
    public void logout(int sessionId) throws SQLException {
        // Elimina la sessione
        sessioneRepository.deleteSessione(sessionId);
    }

    public CreateUtenteResponse getProfile(int sessionId) throws SQLException {
        //1. Recuperare la sessione dal database
        Sessione s = sessioneRepository.getSessioneById(sessionId);
        //2. Recuperare l'id partecipante della sessione
        int utenteId = s.getId_utente();
        //3. Recupero il partecipante dal database
        try {
            return utenteService.getUtenteById(utenteId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}