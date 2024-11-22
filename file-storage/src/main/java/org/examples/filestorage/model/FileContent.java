package org.examples.filestorage.model;

import jakarta.persistence.*;

import java.sql.Blob;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class FileContent {
    private Long id;
    private UUID uid;
    private byte[] content;

    public FileContent() {
    }

    public FileContent(Long id, UUID uid, byte[] content) {
        this.id = id;
        this.uid = uid;
        this.content = content;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false, updatable = false, unique = true)
    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    @Lob
    @Basic(fetch = LAZY)
    @Column(nullable = false)
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
