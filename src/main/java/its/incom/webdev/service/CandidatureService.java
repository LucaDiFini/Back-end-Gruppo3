package its.incom.webdev.service;

import its.incom.webdev.rest.model.CandidaturaResponse;
import its.incom.webdev.persistence.repository.CandidaturaRepository;
import its.incom.webdev.rest.model.ChangeEsitoRequest;
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

    public void setEsito(ChangeEsitoRequest request){
        candidaturaRepository.setEsitoCandidatura(request.getId_candidatura(),request.getEsito());
    }
}
