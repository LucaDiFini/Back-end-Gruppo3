package its.incom.webdev.service;

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
    //private final UtenteService utenteService;//utenteservice da fare
    private final HashCalculator hashCalculator;
    private final SessioneRepository sessioneRepository;

    public AuthenticationService(
            UtenteRepository utenteRepository,
            //UtenteService utenteService,
            HashCalculator hashCalculator,
            SessioneRepository sessioneRepository
    ) {
        this.utenteRepository = utenteRepository;
        //this.utenteService = utenteService;
        this.hashCalculator = hashCalculator;
        this.sessioneRepository = sessioneRepository;
    }

    public int login(String email, String password) throws WrongUsernameOrPasswordException, SessionCreationException {
        String hash = hashCalculator.calculateHash(password);
        Optional<Utente> maybePartecipante = utenteRepository.findByEmailPsw(email, hash);
        if (maybePartecipante.isPresent()) {
            Utente p = maybePartecipante.get();
            try {
                //manca
                int sessione = sessioneRepository.insertSessione(p.getId());
                return sessione;
            } catch (SQLException e) {

                throw new SessionCreationException(e);
            }
        } else {
            throw new WrongUsernameOrPasswordException();
        }
    }

    //gestire   SQLEXCEPTION
    public void logout(int sessionId) throws SQLException {
        sessioneRepository.deleteSessione(sessionId);
    }

    //gestire   SQLEXCEPTION
    //manca classe createpartecipanteresponse
    /*public CreatePartecipanteResponse getProfile(int sessionId) throws SQLException {
        Sessione s = sessioneRepository.getSessioneById(sessionId);
        int partecianteId = s.getUtenteId();
        //manca metodo getUtenteById
        return utenteService.getUtenteById(partecianteId);
    }*/
}

