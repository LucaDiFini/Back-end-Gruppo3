package its.incom.webdev.persistence.model;

public class Candidatura {
    private int id;
    private int id_utente;
    private int id_corso;
    private EsitoCandidatura esito;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_user) {
        this.id_utente = id_user;
    }

    public int getId_corso() {
        return id_corso;
    }

    public void setId_corso(int id_corso) {
        this.id_corso = id_corso;
    }

    public EsitoCandidatura getEsito() {
        return esito;
    }

    public void setEsito(EsitoCandidatura passato) {
        this.esito = passato;
    }


    @Override
    public String toString() {
        return "Candidatura{" +
                ", id_utente=" + id_utente +
                ", id_corso=" + id_corso +
                ", esito=" + esito +
                '}';
    }
}
