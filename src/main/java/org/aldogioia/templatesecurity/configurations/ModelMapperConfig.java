package org.aldogioia.templatesecurity.configurations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dto.CustomerCreateDto;
import org.aldogioia.templatesecurity.data.entities.Customer;
import org.aldogioia.templatesecurity.data.enumerators.Role;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configurazione per il bean {@link ModelMapper}.
 * <p>
 * Definisce e configura un'istanza di {@link ModelMapper} per la mappatura automatica tra oggetti.
 * Abilita il matching dei campi e imposta il livello di accesso ai campi pubblici.
 */
@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, String> passwordConverter = context -> passwordEncoder.encode(context.getSource());

        modelMapper.addMappings(
                new PropertyMap<CustomerCreateDto, Customer>() {
                    @Override
                    protected void configure() {
                        map().setRole(Role.ROLE_CUSTOMER);
                        using(passwordConverter).map().setPassword(source.getPassword());
                    }
                }
        );

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);

        return modelMapper;
    }
}
