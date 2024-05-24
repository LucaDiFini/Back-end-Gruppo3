package its.incom.webdev.persistence.repository;

import its.incom.webdev.persistence.model.Candidatura;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class CandidaturaRepository {

    private final DataSource database;

    public CandidaturaRepository(DataSource database) {
        this.database = database;
    }

    public Candidatura createCandidatura(int idUtente, int idCorso) throws SQLException {

        if (checkCandidatura(idUtente,idCorso)){
            throw new BadRequestException("Candidatura già inviata");
        }

        String query = "INSERT INTO Candidatura (id_utente,id_corso) VALUES (?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idUtente);
                statement.setInt(2, idCorso);
                statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante la creazione della candidatura", e);
        }
            Candidatura c=new Candidatura();
            c.setId_corso(idCorso);
            c.setId_utente(idUtente);
            return c;
    }

    private boolean checkCandidatura(int idU, int idC) {
        String query = "SELECT COUNT(*) FROM Candidatura WHERE id_utente = ? AND id_corso = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idU);
                statement.setInt(2, idC);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    } else {
                        return false;
                    }
                }
        } catch (SQLException e) {
            // Log the exception (use a logging framework or print the stack trace)
            e.printStackTrace();
            throw new RuntimeException("Errore durante il controllo della candidatura", e);
        }
    }
}
