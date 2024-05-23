package its.incom.webdev.service;


import its.incom.webdev.persistence.model.CreateUtenteRequest;
import its.incom.webdev.persistence.model.CreateUtenteResponse;

import its.incom.webdev.persistence.model.Ruolo;
import its.incom.webdev.persistence.model.Utente;

import its.incom.webdev.persistence.repository.UtenteRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
@ApplicationScoped
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final HashCalculator hashCalculator;

    // Costruttore con iniezione delle dipendenze
    public UtenteService(UtenteRepository utenteRepository, HashCalculator hashCalculator) {
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
}