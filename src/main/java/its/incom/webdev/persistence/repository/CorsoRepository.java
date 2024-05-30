package its.incom.webdev.persistence.repository;

import its.incom.webdev.persistence.model.Categoria;
import its.incom.webdev.persistence.model.Corso;
import its.incom.webdev.rest.model.CreateCorsoRequest;
import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
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
        String query = "SELECT nome, categoria, data_inizio, data_fine,n_posti FROM Corso WHERE categoria = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, categoria);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    Corso corso = new Corso();
                    corso.setNome(resultSet.getString("nome"));
                    corso.setCategoria(Categoria.valueOf(resultSet.getString("categoria")));
                    corso.setDataInizio(resultSet.getDate("data_inizio").toLocalDate());
                    if(resultSet.getDate("data_fine")!=null){
                        corso.setDataFine(resultSet.getDate("data_fine").toLocalDate());
                    }
                    corso.setN_posti(resultSet.getInt("n_posti"));

                    corsi.add(corso);


                }
                return corsi;
            }

        } catch (SQLException e) {
            System.err.println("Errore durante l'esecuzione della query SQL: " + e.getMessage());
        }
        return null;
    }

    public List<Corso> getCorsi() throws SQLException {
        ArrayList<Corso> corsi = new ArrayList<>();
        String query = "SELECT nome, categoria, data_inizio, data_fine,n_posti FROM Corso";
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    Corso corso = new Corso();
                    corso.setNome(resultSet.getString("nome"));
                    corso.setCategoria(Categoria.valueOf(resultSet.getString("categoria")));
                    corso.setDataInizio(resultSet.getDate("data_inizio").toLocalDate());
                    if(resultSet.getDate("data_fine")!=null){
                        corso.setDataFine(resultSet.getDate("data_fine").toLocalDate());
                    }

                    corso.setN_posti(resultSet.getInt("n_posti"));

                    corsi.add(corso);
                }
                return corsi;
            }
        }
    }
    public Corso getCorsoById(int id) throws SQLException {
        String query = "SELECT nome, categoria, data_inizio, data_fine,n_posti FROM Corso WHERE id=?";
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    Corso corso = new Corso();
                    corso.setNome(resultSet.getString("nome"));
                    corso.setCategoria(Categoria.valueOf(resultSet.getString("categoria")));
                    corso.setDataInizio(resultSet.getDate("data_inizio").toLocalDate());
                    if(resultSet.getDate("data_fine")!=null){
                        corso.setDataFine(resultSet.getDate("data_fine").toLocalDate());
                    }
                    corso.setN_posti(resultSet.getInt("n_posti"));
                    return corso;
                }
            }
        }
        return null;
    }

    public boolean checkCorso(String nome, LocalDate data_inizio) {
        String query = "SELECT COUNT(*)\n" +
                        "FROM Corso\n" +
                        "WHERE nome = ?\n" +
                        "AND YEAR(data_inizio) = YEAR(?);";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setDate(2, Date.valueOf(data_inizio));

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
            throw new RuntimeException("Errore durante il controllo di inserimento del corso ", e);
        }
    }

    public CreateCorsoRequest createCorso(CreateCorsoRequest corso) throws SQLException {
        String query = "INSERT INTO Corso(nome, categoria, data_inizio, data_fine,n_posti) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, corso.getNome());
            statement.setString(2, String.valueOf(corso.getCategoria()));
            statement.setDate(3, Date.valueOf(corso.getData_inizio()));
            if(corso.getData_fine()!=null){
                statement.setDate(4, Date.valueOf(corso.getData_fine()));
            }else{
                statement.setDate(4, null);
            }

            //controllare se non è settato
            if(((Integer)corso.getN_posti())!=null){
                statement.setInt(5,corso.getN_posti());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante la creazione del corso", e);
        }

        return corso;
    }
}
