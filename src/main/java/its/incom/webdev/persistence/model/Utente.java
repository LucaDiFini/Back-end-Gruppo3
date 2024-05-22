package its.incom.webdev.persistence.model;

import java.time.LocalDate;

public class Utente {
    private int id;
    private String nome;
    private String cognome;
    private String passwordHash;
    private String email;
    private LocalDate dataRegistrazione;
    private Ruolo ruolo;


    public Utente(String nome, int id, String cognome, String passwordHash, String email, LocalDate dataRegistrazione, Ruolo ruolo) {
        this.nome = nome;
        this.id = id;
        this.cognome = cognome;
        this.passwordHash = passwordHash;
        this.email = email;
        this.dataRegistrazione = dataRegistrazione;
        this.ruolo = ruolo;
    }
    public Utente(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
