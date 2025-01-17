package org.examples.auditing.model;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Person extends AuditModel<Integer> {

    private String name;
    private String lastname;
    private String email;

}
