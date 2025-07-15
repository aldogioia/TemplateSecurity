package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.Blacklist;
import org.aldogioia.templatesecurity.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Interfaccia DAO per l'entità {@link User}.
 * <p>
 * Estende {@link JpaRepository} fornendo metodi CRUD e query personalizzate
 * per la gestione degli utenti nel database.
 */
@Repository
public interface BlacklistDao extends JpaRepository<Blacklist,String> {
    /**
     * Verifica se un token è presente nella blacklist.
     *
     * @param token il token da verificare
     * @return {@code true} se il token è in blacklist, altrimenti {@code false}
     */
    boolean existsByToken(String token);

    /**
     * Elimina tutti i token in blacklist scaduti prima della data specificata.
     *
     * @param expiration la data di scadenza limite
     */
    void deleteAllByExpirationBefore(Date expiration);
}
