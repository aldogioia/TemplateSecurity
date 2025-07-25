package org.aldogioia.templatesecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TemplateSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(TemplateSecurityApplication.class, args);
    }
}
