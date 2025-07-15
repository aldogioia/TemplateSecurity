package org.aldogioia.templatesecurity.service.interfaces;

/**
 * Interfaccia per la gestione della blacklist dei token.
 * <p>
 * Fornisce metodi per aggiungere token alla blacklist e verificare se un token è stato inserito.
 */
public interface BlacklistService {
    /**
     * Aggiunge un token alla blacklist.
     *
     * @param token il token da inserire in blacklist
     */
    void addTokenToBlacklist(String token);
    /**
     * Verifica se un token è presente nella blacklist.
     *
     * @param token il token da verificare
     * @return {@code true} se il token è in blacklist, altrimenti {@code false}
     */
    boolean isTokenBlacklisted(String token);
}
