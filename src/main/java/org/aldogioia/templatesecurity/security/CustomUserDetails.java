package org.aldogioia.templatesecurity.security;

import org.aldogioia.templatesecurity.data.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementazione personalizzata di {@link UserDetails} per l'entità {@link User}.
 * <p>
 * Adatta l'entità utente del dominio per l'utilizzo con Spring Security,
 * fornendo le informazioni necessarie per l'autenticazione e l'autorizzazione.
 */
public record CustomUserDetails(User user) implements UserDetails {
    public String getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhoneNumber();
    }
}
