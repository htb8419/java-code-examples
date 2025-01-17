package org.examples.auditing.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseModel<T> {
    private T id;
    private UUID uid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false, updatable = false, columnDefinition = "UUID")
    @UuidGenerator
    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}
