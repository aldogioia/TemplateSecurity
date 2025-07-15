package org.aldogioia.templatesecurity.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configurazione per l'auditing JPA.
 * <p>
 * Abilita il supporto per l'auditing delle entità JPA tramite l'annotazione {@link EnableJpaAuditing}.
 * Utilizza il bean "auditorProvider" per determinare l'auditor corrente.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {
}
