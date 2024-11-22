package org.examples.filestorage.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class FileInfo {

    private Long id;
    private UUID uid;
    private String filename;
    private Long fileSize;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false, updatable = false, unique = true)
    @UuidGenerator
    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String name) {
        this.filename = name;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

}