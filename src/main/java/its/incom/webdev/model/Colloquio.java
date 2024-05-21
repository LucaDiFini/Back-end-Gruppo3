package its.incom.webdev.model;

import java.time.LocalDate;

public class Colloquio {
    private int id;
    private int id_user;
    private int id_candidatura;
    private LocalDate dataOrario;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_candidatura() {
        return id_candidatura;
    }

    public void setId_candidatura(int id_candidatura) {
        this.id_candidatura = id_candidatura;
    }

    public LocalDate getDataOrario() {
        return dataOrario;
    }

    public void setDataOrario(LocalDate dataOrario) {
        this.dataOrario = dataOrario;
    }

    @Override
    public String toString() {
        return "Colloquio{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", id_candidatura=" + id_candidatura +
                ", dataOrario=" + dataOrario +
                '}';
    }


}
