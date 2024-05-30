package its.incom.webdev.rest.model;

import its.incom.webdev.persistence.model.EsitoCandidatura;

public class ChangeEsitoRequest {
    private int id_candidatura;
    private EsitoCandidatura esito;

    public int getId_candidatura() {
        return id_candidatura;
    }

    public void setId_candidatura(int id_candidatura) {
        this.id_candidatura = id_candidatura;
    }

    public EsitoCandidatura getEsito() {
        return esito;
    }

    public void setEsito(EsitoCandidatura esito) {
        this.esito = esito;
    }
}
