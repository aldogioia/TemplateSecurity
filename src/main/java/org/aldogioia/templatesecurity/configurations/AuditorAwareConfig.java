package org.aldogioia.templatesecurity.configurations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.security.logging.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * Configurazione per l'auditing delle entità JPA.
 * <p>
 * Definisce un bean {@link AuditorAware} che fornisce l'utente corrente
 * responsabile delle modifiche ai dati, utilizzando l'implementazione {@link AuditorAwareImpl}.
 */
@Configuration
@RequiredArgsConstructor
public class AuditorAwareConfig {
    private final AuditorAwareImpl auditorAwareImpl;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return auditorAwareImpl;
    }
}
