package its.incom.webdev.persistence.model;

public class CandidaturaResponse {
    private int id_candidatura;
    private int id_utente;
    private int id_corso;
    private boolean passato;
    private String nome_utente;
    private String cognome_utente;
    private String nome_corso;
    public CandidaturaResponse(){
        
    }


    public CandidaturaResponse(int id_candidatura, int id_utente, int id_corso, boolean passato_candidatura, String nome_utente, String cognome_utente) {
        this.id_candidatura = id_candidatura;
        this.id_utente = id_utente;
        this.id_corso = id_corso;
        this.passato = passato_candidatura;
        this.nome_utente = nome_utente;
        this.cognome_utente = cognome_utente;
    }

    public int getId_candidatura() {
        return id_candidatura;
    }

    public void setId_candidatura(int id_candidatura) {
        this.id_candidatura = id_candidatura;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }

    public int getId_corso() {
        return id_corso;
    }

    public void setId_corso(int id_corso) {
        this.id_corso = id_corso;
    }

    public boolean isPassato() {
        return passato;
    }

    public void setPassato(boolean passato_candidatura) {
        this.passato = passato_candidatura;
    }

    public String getNome_utente() {
        return nome_utente;
    }

    public void setNome_utente(String nome_utente) {
        this.nome_utente = nome_utente;
    }

    public String getCognome_utente() {
        return cognome_utente;
    }

    public void setCognome_utente(String cognome_utente) {
        this.cognome_utente = cognome_utente;
    }
    public String getNome_corso() {
        return nome_corso;
    }

    public void setNome_corso(String nome_corso) {
        this.nome_corso = nome_corso;
    }
}
