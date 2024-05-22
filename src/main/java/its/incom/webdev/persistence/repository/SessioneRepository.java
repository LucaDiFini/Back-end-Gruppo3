    package its.incom.webdev.persistence.repository;

    import its.incom.webdev.persistence.model.Sessione;

    import javax.sql.DataSource;
    import java.sql.*;
    import jakarta.enterprise.context.ApplicationScoped;

    @ApplicationScoped
    public class SessioneRepository {

        private final DataSource database;

        // Costruttore con iniezione delle dipendenze
        public SessioneRepository(DataSource database) {
            this.database = database;
        }

        // Metodo per inserire una nuova sessione nel database
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

        // Metodo per eliminare una sessione dal database
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

        // Metodo per ottenere una sessione dal database tramite il suo ID
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