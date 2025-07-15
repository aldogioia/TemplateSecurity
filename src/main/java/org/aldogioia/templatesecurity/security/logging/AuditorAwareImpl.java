package org.aldogioia.templatesecurity.security.logging;

import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.service.implementations.UserDetailsServiceImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementazione di {@link AuditorAware} per fornire l'auditor corrente.
 * <p>
 * Questa classe viene utilizzata da Spring Data JPA per popolare automaticamente
 * i campi di auditing (ad esempio, createdBy, modifiedBy) nelle entità.
 * Attualmente restituisce sempre "system" come identificativo dell'auditor.
 */
@Component
@NonNullApi
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    @Override
    public Optional<String> getCurrentAuditor() {
        try{
            return Optional.of(userDetailsServiceImpl.getCurrentUser());
        }catch (Exception e){
            return Optional.of("system");
        }
    }
}
