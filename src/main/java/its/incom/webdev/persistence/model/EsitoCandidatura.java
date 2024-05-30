package its.incom.webdev.persistence.model;

public enum EsitoCandidatura {
    IN_ATTESA,
    CONVOCATO_COLLOQUIO,
    BOCCIATO,
    AMMESSO,
    AMMESSO_AD_UN_ALTRO_CORSO;
    public String toDatabaseValue() {
        switch (this) {
            case IN_ATTESA:
                return "In Attesa";
            case CONVOCATO_COLLOQUIO:
                return "Convocato Colloquio";
            case BOCCIATO:
                return "Bocciato";
            case AMMESSO:
                return "Ammesso";
            case AMMESSO_AD_UN_ALTRO_CORSO:
                return "Ammesso ad un altro corso";
            default:
                throw new IllegalArgumentException("Esito non valido: " + this);
        }
    }
}
