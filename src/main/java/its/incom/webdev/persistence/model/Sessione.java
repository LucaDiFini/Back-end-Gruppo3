package its.incom.webdev.persistence.model;

import java.sql.Timestamp;

public class Sessione {
    private int id;
    private Timestamp data;
    private int id_utente;


    // Costruttore senza parametri
    public Sessione() {
    }

    // Costruttore con parametri
    public Sessione(int id, Timestamp data, int id_utente) {
        this.id = id;
        this.data = data;
        this.id_utente = id_utente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }


}
