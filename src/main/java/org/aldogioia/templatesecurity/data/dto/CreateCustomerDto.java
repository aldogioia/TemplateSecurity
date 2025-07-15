package org.aldogioia.templatesecurity.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCustomerDto {
    @NotNull
    @Size(min = 3, max = 50, message = "Il nome deve essere compreso tra 3 e 50 caratteri")
    private String name;

    @NotNull
    @Size(min = 3, max = 50, message = "Il cognome deve essere compreso tra 3 e 50 caratteri")
    private String surname;

    @NotNull
    @Pattern(regexp = "^[0-9]{10}$", message = "Il numero di telefono deve essere un numero di 10 cifre")
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "^(?=.*[!@#])(?=.*\\d).{8,}$", message = "La password deve avere almeno 8 caratteri, almeno un numero e almeno uno tra i caratteri speciali !@#")
    private String password;
}
