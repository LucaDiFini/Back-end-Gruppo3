package its.incom.webdev.persistence.repository;

import its.incom.webdev.persistence.model.Candidatura;
import its.incom.webdev.persistence.model.CandidaturaResponse;
import its.incom.webdev.persistence.model.Categoria;
import its.incom.webdev.persistence.model.Corso;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String query = "SELECT COUNT(id) FROM Candidatura WHERE id_utente = ? AND id_corso = ?";

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

    public List<CandidaturaResponse> getCandidature() throws SQLException {
        ArrayList<CandidaturaResponse> candidature = new ArrayList<>();
        String query = "SELECT c.id, c.id_utente, c.id_corso, c.passato,u.nome,u.cognome,c2.nome " +
                       "FROM Candidatura AS c JOIN Corso AS c2 ON c.id_corso=c2.id JOIN Utente AS u ON c.id_utente=u.id";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    CandidaturaResponse candidatura = new CandidaturaResponse();
                    candidatura.setId_candidatura(resultSet.getInt("c.id"));
                    candidatura.setId_utente(resultSet.getInt("c.id_utente"));
                    candidatura.setId_corso(resultSet.getInt("c.id_corso"));
                    candidatura.setPassato(resultSet.getBoolean("c.passato"));
                    candidatura.setNome_utente(resultSet.getString("u.nome"));
                    candidatura.setCognome_utente(resultSet.getString("u.cognome"));
                    candidatura.setNome_corso(resultSet.getString("c2.nome"));


                    candidature.add(candidatura);
                }
                return candidature;
            }
        }
    }

    public CandidaturaResponse getCandidatura(int id) throws SQLException {
        String query = "SELECT c.id_utente, c.id_corso, c.passato,u.nome,u.cognome,c2.nome " +
                "FROM Candidatura AS c JOIN Corso AS c2 ON c.id_corso=c2.id JOIN Utente AS u ON c.id_utente=u.id WHERE c.id=?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    CandidaturaResponse candidatura = new CandidaturaResponse();
                    candidatura.setId_candidatura(id);
                    candidatura.setId_utente(resultSet.getInt("c.id_utente"));
                    candidatura.setId_corso(resultSet.getInt("c.id_corso"));
                    candidatura.setPassato(resultSet.getBoolean("c.passato"));
                    candidatura.setNome_utente(resultSet.getString("u.nome"));
                    candidatura.setCognome_utente(resultSet.getString("u.cognome"));
                    candidatura.setNome_corso(resultSet.getString("c2.nome"));
                    return candidatura;
                }

            }
        }
        return null;
    }
}
