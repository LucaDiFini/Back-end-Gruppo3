package its.incom.webdev.service;

import its.incom.webdev.persistence.model.CandidaturaResponse;
import its.incom.webdev.persistence.repository.CandidaturaRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class CandidatureService {
    private final CandidaturaRepository candidaturaRepository;

    public CandidatureService(CandidaturaRepository candidaturaRepository) {
        this.candidaturaRepository = candidaturaRepository;
    }

    public List<CandidaturaResponse> getCandidatureOfUtente(int id){
        try {
            return candidaturaRepository.getCandidatureByUtente(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
