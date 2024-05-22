package its.incom.webdev.service;


import its.incom.webdev.persistence.model.CreateUtenteResponse;

import its.incom.webdev.persistence.model.Utente;

import its.incom.webdev.persistence.repository.UtenteRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.SQLException;
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
    public CreateUtenteResponse getUtenteById(int utenteId) throws SQLException {
        Optional<Utente> utente = utenteRepository.getUtenteById(utenteId);
        if (utente.isPresent()) {
            CreateUtenteResponse response = convertToResponse(utente.get());
            return response;
        } else {
            // Gestisci il caso in cui l'utente non esiste
            // Potrebbe essere lanciando un'eccezione o restituendo un valore predefinito
            throw new SQLException("Utente non trovato con id: " + utenteId);
        }
    }
}