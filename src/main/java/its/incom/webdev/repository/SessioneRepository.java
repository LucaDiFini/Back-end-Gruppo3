package its.incom.webdev.repository;

import its.incom.webdev.model.Sessione;

import javax.sql.DataSource;
import java.sql.*;

public class SessioneRepository {

    private final DataSource database;

    public SessioneRepository(DataSource database) {
        this.database = database;
    }

    public int insertSessione(int utenteId) throws SQLException {
        try (Connection connection = database.getConnection()) {
            String query = "INSERT INTO Sessione (utenteId) VALUES (?)";

            try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, utenteId);
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return id;
                }

            }
            throw new SQLException("Cannot insert new session for partecipante" + utenteId);
        }

    }

    public void deleteSessione(int sessionId) throws SQLException {
        try (Connection connection = database.getConnection()) {
            String query = "DELETE FROM Sessione WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, sessionId);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Cancellazione della sessione fallita, nessuna riga eliminata.");
                }
            }
        }
    }

    public Sessione getSessioneById(int sessionId) throws SQLException {
        try (Connection connection = database.getConnection()) {
            String query = "SELECT * FROM Sessione WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, sessionId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        Timestamp dataCreazione = resultSet.getTimestamp("dataCreazione");
                        int utenteId = resultSet.getInt("utenteId");
                        return new Sessione(id, dataCreazione, utenteId);
                    } else {
                        return null; // Sessione non trovata
                    }
                }
            }
        }
    }


}



