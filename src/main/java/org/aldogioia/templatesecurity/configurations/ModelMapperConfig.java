package org.aldogioia.templatesecurity.configurations;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurazione per il bean {@link ModelMapper}.
 * <p>
 * Definisce e configura un'istanza di {@link ModelMapper} per la mappatura automatica tra oggetti.
 * Abilita il matching dei campi e imposta il livello di accesso ai campi pubblici.
 */
@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);

        return modelMapper;
    }
}
