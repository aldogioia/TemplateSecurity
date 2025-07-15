package org.aldogioia.templatesecurity.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.aldogioia.templatesecurity.security.logging.Auditable;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "blacklist")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Blacklist extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiration", nullable = false)
    private Date expiration;
}
