package its.incom.webdev.service;


import its.incom.webdev.persistence.model.*;

import its.incom.webdev.persistence.repository.SessioneRepository;
import its.incom.webdev.persistence.repository.UtenteRepository;
import its.incom.webdev.rest.model.CreateUtenteRequest;
import its.incom.webdev.rest.model.CreateUtenteResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class UtenteService {

    private final SessioneRepository sessioneRepository;
    private final UtenteRepository utenteRepository;
    private final HashCalculator hashCalculator;

    // Costruttore con iniezione delle dipendenze
    public UtenteService(SessioneRepository sessioneRepository, UtenteRepository utenteRepository, HashCalculator hashCalculator) {
        this.sessioneRepository = sessioneRepository;
        this.utenteRepository = utenteRepository;
        this.hashCalculator = hashCalculator;
    }

    // Metodo privato per convertire un oggetto Utente in un oggetto CreateUtenteResponse
    private CreateUtenteResponse convertToResponse(Utente utente) {
        CreateUtenteResponse response = new CreateUtenteResponse();
        response.setId(utente.getId());
        response.setNome(utente.getNome());
        response.setCognome(utente.getCognome());
        response.setEmail(utente.getEmail());
        response.setRuolo(utente.getRuolo());

        return response;
    }

    // Metodo pubblico per ottenere un utente dal database tramite il suo ID
    // Restituisce un oggetto CreateUtenteResponse se l'utente esiste, altrimenti lancia un'eccezione
    public CreateUtenteResponse getUtenteById(int id_utente) throws SQLException {
        Optional<Utente> utente = utenteRepository.getUtenteById(id_utente);
        if (utente.isPresent()) {
            CreateUtenteResponse response = convertToResponse(utente.get());
            return response;
        } else {
            // Gestisci il caso in cui l'utente non esiste
            // Potrebbe essere lanciando un'eccezione o restituendo un valore predefinito
            throw new SQLException("Utente non trovato con id: " + id_utente);
        }
    }

    public Utente ConvertRequestToUtente(CreateUtenteRequest cur){
        Utente u=new Utente();
        u.setEmail(cur.getEmail());
        u.setNome(cur.getNome());
        u.setPasswordHash(hashCalculator.calculateHash(cur.getPassword()));
        u.setCognome(cur.getCognome());
        u.setRuolo(Ruolo.S);
        u.setDataRegistrazione(LocalDate.now());
        return u;
    }

    public List<Utente> getAllUtenti(){
        //chiamata select
        return utenteRepository.getUtenti();
    }

    public void setRuoloOfUser(int id,String ruolo){
        utenteRepository.setRuolo(id,ruolo);
    }

    public  boolean isAdmin(int sessionId){
        try {
            Sessione s=sessioneRepository.getSessioneById(sessionId);
            Optional<Utente> utente=utenteRepository.getUtenteById(s.getId_utente());
            if(utente.get().getRuolo()==Ruolo.A){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  boolean isDocente(int sessionId){
        try {
            Sessione s=sessioneRepository.getSessioneById(sessionId);
            Optional<Utente> utente=utenteRepository.getUtenteById(s.getId_utente());
            if(utente.get().getRuolo()==Ruolo.D){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}