package org.aldogioia.templatesecurity.data.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("customers")
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {
}
