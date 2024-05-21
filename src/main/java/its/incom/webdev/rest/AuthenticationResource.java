package its.incom.webdev.rest;

import its.incom.webdev.repository.UtenteRepository;

public class AuthenticationResource {
    private final UtenteRepository utenteRepository;

    public AuthenticationResource(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }
}
