package org.examples.envers.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractModel {

    private Integer id;
    private UUID uid;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false, updatable = false)
    @UuidGenerator
    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created) {
        this.createdAt = created;
    }

    @Column(nullable = false)
    @UpdateTimestamp
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modified) {
        this.modifiedAt = modified;
    }
}
