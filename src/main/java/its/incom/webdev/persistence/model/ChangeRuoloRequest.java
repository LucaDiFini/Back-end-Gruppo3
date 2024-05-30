package its.incom.webdev.persistence.model;

public class ChangeRuoloRequest {
    private int id_utente;
    private String new_ruolo;

    public String getNew_ruolo() {
        return new_ruolo;
    }

    public void setNew_ruolo(String new_ruolo) {
        this.new_ruolo = new_ruolo;
    }



    // Getters e setters
    public int getId_utente() {
        return id_utente;
    }


    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }


}