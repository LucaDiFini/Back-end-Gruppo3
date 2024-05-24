package its.incom.webdev.persistence.repository;

import its.incom.webdev.persistence.model.Categoria;
import its.incom.webdev.persistence.model.Corso;
import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class CorsoRepository {

    private final DataSource database;

    public CorsoRepository(DataSource database) {
        this.database = database;
    }

    public List<Corso> getCorsiByCategoria(String categoria) {
        ArrayList<Corso> corsi = new ArrayList<>();
        String query = "SELECT nome, categoria, data_inizio, data_fine FROM Corso WHERE categoria = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, categoria);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    Corso corso = new Corso();
                    corso.setNome(resultSet.getString("nome"));
                    corso.setCategoria(Categoria.valueOf(resultSet.getString("categoria")));
                    corso.setDataInizio(resultSet.getDate("data_inizio").toLocalDate());
                    corso.setDataFine(resultSet.getDate("data_fine").toLocalDate());

                    corsi.add(corso);


                }
                return corsi;
            }

        } catch (SQLException e) {
            System.err.println("Errore durante l'esecuzione della query SQL: " + e.getMessage());
        }
        return null;
    }
}
