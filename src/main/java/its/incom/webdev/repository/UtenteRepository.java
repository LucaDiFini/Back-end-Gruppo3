package its.incom.webdev.repository;

import its.incom.webdev.model.Utente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class UtenteRepository {

    private final DataSource database;

    public UtenteRepository(DataSource database) {
        this.database = database;
    }

    public Utente createUtente(Utente utente) throws SQLException {
        if (checkUtente(utente.getEmail(), utente.getPasswordHash())) {
            throw new BadRequestException("Utente già esistente");
        }

        String query = "INSERT INTO Utente (nome, cognome, email, passwordHash, dataRegistrazione, ruolo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, utente.getNome());
            statement.setString(2, utente.getCognome());
            statement.setString(3, utente.getEmail());
            statement.setString(4, utente.getPasswordHash());
            statement.setObject(5, utente.getDataRegistrazione());
            statement.setObject(6, utente.getRuolo().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            // Log the exception (use a logging framework or print the stack trace)
            e.printStackTrace();
            throw new RuntimeException("Errore durante la creazione dell'utente", e);
        }

        return utente;
    }


    private boolean checkUtente(String email, String passwordHash) {
        String query = "SELECT COUNT(*) FROM Utente WHERE email = ? AND passwordHash = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, passwordHash);

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
            throw new RuntimeException("Errore durante il controllo dell'utente", e);
        }
    }


}
