package its.incom.webdev.persistence.model;

import java.sql.Timestamp;

public class Sessione {
    private int id;
    private Timestamp dataCreazione;
    private int utenteId;


    // Costruttore senza parametri
    public Sessione() {
    }

    // Costruttore con parametri
    public Sessione(int id, Timestamp dataCreazione, int utenteId) {
        this.id = id;
        this.dataCreazione = dataCreazione;
        this.utenteId = utenteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Timestamp dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public int getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(int utenteId) {
        this.utenteId = utenteId;
    }


}
