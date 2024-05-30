package its.incom.webdev.service;

import its.incom.webdev.rest.model.CreateCorsoRequest;
import its.incom.webdev.persistence.repository.CorsoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

import java.sql.SQLException;


@ApplicationScoped
public class CorsiService {
    private CorsoRepository corsoRepository;

    public CorsiService(CorsoRepository corsoRepository) {
        this.corsoRepository = corsoRepository;
    }


    public CreateCorsoRequest newCorso(CreateCorsoRequest request){
        if(corsoRepository.checkCorso(request.getNome(),request.getData_inizio())){
            throw new BadRequestException("Corso già esistente");
        }
        try {
            return corsoRepository.createCorso(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
