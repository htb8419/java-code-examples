package org.examples.jdbc.model;

import jakarta.persistence.*;

import java.sql.Blob;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Table(name = "TST_FILE_STORAGE")
public class FileStorage {
    private Integer id;
    private String fileName;
    private String fileContentType;
    private Blob fileContent;

    public FileStorage() {
    }

    public FileStorage(String fileName, String fileContentType, Blob fileContent) {
        this.fileName = fileName;
        this.fileContentType=fileContentType;
        this.fileContent = fileContent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    @Lob()
    @Basic(fetch=LAZY)
    public Blob getFileContent() {
        return fileContent;
    }

    public void setFileContent(Blob fileContent) {
        this.fileContent = fileContent;
    }
}
