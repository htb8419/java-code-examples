package org.examples.auditing.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditModel<T> extends BaseModel<T> {

    @CreatedBy
    @Column(updatable = false, nullable = false)
    private Integer createdBy;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedBy
    @Column(nullable = false)
    private Integer lastModifiedBy;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;
}
